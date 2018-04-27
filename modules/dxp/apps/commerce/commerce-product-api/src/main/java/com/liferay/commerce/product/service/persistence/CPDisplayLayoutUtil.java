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

package com.liferay.commerce.product.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDisplayLayout;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cp display layout service. This utility wraps {@link com.liferay.commerce.product.service.persistence.impl.CPDisplayLayoutPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPDisplayLayoutPersistence
 * @see com.liferay.commerce.product.service.persistence.impl.CPDisplayLayoutPersistenceImpl
 * @generated
 */
@ProviderType
public class CPDisplayLayoutUtil {
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
	public static void clearCache(CPDisplayLayout cpDisplayLayout) {
		getPersistence().clearCache(cpDisplayLayout);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CPDisplayLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CPDisplayLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CPDisplayLayout> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CPDisplayLayout update(CPDisplayLayout cpDisplayLayout) {
		return getPersistence().update(cpDisplayLayout);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CPDisplayLayout update(CPDisplayLayout cpDisplayLayout,
		ServiceContext serviceContext) {
		return getPersistence().update(cpDisplayLayout, serviceContext);
	}

	/**
	* Returns all the cp display layouts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching cp display layouts
	*/
	public static List<CPDisplayLayout> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the cp display layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @return the range of matching cp display layouts
	*/
	public static List<CPDisplayLayout> findByUuid(String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the cp display layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp display layouts
	*/
	public static List<CPDisplayLayout> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPDisplayLayout> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp display layouts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp display layouts
	*/
	public static List<CPDisplayLayout> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first cp display layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp display layout
	* @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout findByUuid_First(String uuid,
		OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first cp display layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout fetchByUuid_First(String uuid,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last cp display layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp display layout
	* @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout findByUuid_Last(String uuid,
		OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last cp display layout in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout fetchByUuid_Last(String uuid,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the cp display layouts before and after the current cp display layout in the ordered set where uuid = &#63;.
	*
	* @param CPDisplayLayoutId the primary key of the current cp display layout
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp display layout
	* @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	*/
	public static CPDisplayLayout[] findByUuid_PrevAndNext(
		long CPDisplayLayoutId, String uuid,
		OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence()
				   .findByUuid_PrevAndNext(CPDisplayLayoutId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the cp display layouts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of cp display layouts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching cp display layouts
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the cp display layout where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPDisplayLayoutException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp display layout
	* @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout findByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp display layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout fetchByUUID_G(String uuid, long groupId) {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the cp display layout where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the cp display layout where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the cp display layout that was removed
	*/
	public static CPDisplayLayout removeByUUID_G(String uuid, long groupId)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of cp display layouts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching cp display layouts
	*/
	public static int countByUUID_G(String uuid, long groupId) {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the cp display layouts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching cp display layouts
	*/
	public static List<CPDisplayLayout> findByUuid_C(String uuid, long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the cp display layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @return the range of matching cp display layouts
	*/
	public static List<CPDisplayLayout> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the cp display layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching cp display layouts
	*/
	public static List<CPDisplayLayout> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp display layouts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching cp display layouts
	*/
	public static List<CPDisplayLayout> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp display layout
	* @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout findByUuid_C_First(String uuid,
		long companyId, OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout fetchByUuid_C_First(String uuid,
		long companyId, OrderByComparator<CPDisplayLayout> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp display layout
	* @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout findByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout fetchByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<CPDisplayLayout> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the cp display layouts before and after the current cp display layout in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CPDisplayLayoutId the primary key of the current cp display layout
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cp display layout
	* @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	*/
	public static CPDisplayLayout[] findByUuid_C_PrevAndNext(
		long CPDisplayLayoutId, String uuid, long companyId,
		OrderByComparator<CPDisplayLayout> orderByComparator)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(CPDisplayLayoutId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the cp display layouts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of cp display layouts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching cp display layouts
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns the cp display layout where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchCPDisplayLayoutException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching cp display layout
	* @throws NoSuchCPDisplayLayoutException if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout findByC_C(long classNameId, long classPK)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns the cp display layout where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout fetchByC_C(long classNameId, long classPK) {
		return getPersistence().fetchByC_C(classNameId, classPK);
	}

	/**
	* Returns the cp display layout where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching cp display layout, or <code>null</code> if a matching cp display layout could not be found
	*/
	public static CPDisplayLayout fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByC_C(classNameId, classPK, retrieveFromCache);
	}

	/**
	* Removes the cp display layout where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the cp display layout that was removed
	*/
	public static CPDisplayLayout removeByC_C(long classNameId, long classPK)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of cp display layouts where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching cp display layouts
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Caches the cp display layout in the entity cache if it is enabled.
	*
	* @param cpDisplayLayout the cp display layout
	*/
	public static void cacheResult(CPDisplayLayout cpDisplayLayout) {
		getPersistence().cacheResult(cpDisplayLayout);
	}

	/**
	* Caches the cp display layouts in the entity cache if it is enabled.
	*
	* @param cpDisplayLayouts the cp display layouts
	*/
	public static void cacheResult(List<CPDisplayLayout> cpDisplayLayouts) {
		getPersistence().cacheResult(cpDisplayLayouts);
	}

	/**
	* Creates a new cp display layout with the primary key. Does not add the cp display layout to the database.
	*
	* @param CPDisplayLayoutId the primary key for the new cp display layout
	* @return the new cp display layout
	*/
	public static CPDisplayLayout create(long CPDisplayLayoutId) {
		return getPersistence().create(CPDisplayLayoutId);
	}

	/**
	* Removes the cp display layout with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CPDisplayLayoutId the primary key of the cp display layout
	* @return the cp display layout that was removed
	* @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	*/
	public static CPDisplayLayout remove(long CPDisplayLayoutId)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence().remove(CPDisplayLayoutId);
	}

	public static CPDisplayLayout updateImpl(CPDisplayLayout cpDisplayLayout) {
		return getPersistence().updateImpl(cpDisplayLayout);
	}

	/**
	* Returns the cp display layout with the primary key or throws a {@link NoSuchCPDisplayLayoutException} if it could not be found.
	*
	* @param CPDisplayLayoutId the primary key of the cp display layout
	* @return the cp display layout
	* @throws NoSuchCPDisplayLayoutException if a cp display layout with the primary key could not be found
	*/
	public static CPDisplayLayout findByPrimaryKey(long CPDisplayLayoutId)
		throws com.liferay.commerce.product.exception.NoSuchCPDisplayLayoutException {
		return getPersistence().findByPrimaryKey(CPDisplayLayoutId);
	}

	/**
	* Returns the cp display layout with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CPDisplayLayoutId the primary key of the cp display layout
	* @return the cp display layout, or <code>null</code> if a cp display layout with the primary key could not be found
	*/
	public static CPDisplayLayout fetchByPrimaryKey(long CPDisplayLayoutId) {
		return getPersistence().fetchByPrimaryKey(CPDisplayLayoutId);
	}

	public static java.util.Map<java.io.Serializable, CPDisplayLayout> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cp display layouts.
	*
	* @return the cp display layouts
	*/
	public static List<CPDisplayLayout> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the cp display layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @return the range of cp display layouts
	*/
	public static List<CPDisplayLayout> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the cp display layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of cp display layouts
	*/
	public static List<CPDisplayLayout> findAll(int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the cp display layouts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPDisplayLayoutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cp display layouts
	* @param end the upper bound of the range of cp display layouts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of cp display layouts
	*/
	public static List<CPDisplayLayout> findAll(int start, int end,
		OrderByComparator<CPDisplayLayout> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cp display layouts from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cp display layouts.
	*
	* @return the number of cp display layouts
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static CPDisplayLayoutPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDisplayLayoutPersistence, CPDisplayLayoutPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDisplayLayoutPersistence.class);

		ServiceTracker<CPDisplayLayoutPersistence, CPDisplayLayoutPersistence> serviceTracker =
			new ServiceTracker<CPDisplayLayoutPersistence, CPDisplayLayoutPersistence>(bundle.getBundleContext(),
				CPDisplayLayoutPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
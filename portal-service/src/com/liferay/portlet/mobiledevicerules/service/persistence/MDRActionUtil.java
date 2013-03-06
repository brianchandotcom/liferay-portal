/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.mobiledevicerules.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.mobiledevicerules.model.MDRAction;

import java.util.List;

/**
 * The persistence utility for the m d r action service. This utility wraps {@link MDRActionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRActionPersistence
 * @see MDRActionPersistenceImpl
 * @generated
 */
public class MDRActionUtil {
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
	public static void clearCache(MDRAction mdrAction) {
		getPersistence().clearCache(mdrAction);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<MDRAction> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MDRAction> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MDRAction> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static MDRAction update(MDRAction mdrAction)
		throws SystemException {
		return getPersistence().update(mdrAction);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static MDRAction update(MDRAction mdrAction,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(mdrAction, serviceContext);
	}

	/**
	* Returns all the m d r actions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the m d r actions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the m d r actions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first m d r action in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first m d r action in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action, or <code>null</code> if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last m d r action in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last m d r action in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action, or <code>null</code> if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the m d r actions before and after the current m d r action in the ordered set where uuid = &#63;.
	*
	* @param actionId the primary key of the current m d r action
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction[] findByUuid_PrevAndNext(
		long actionId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence()
				   .findByUuid_PrevAndNext(actionId, uuid, orderByComparator);
	}

	/**
	* Removes all the m d r actions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of m d r actions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the m d r action where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchActionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the m d r action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r action, or <code>null</code> if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the m d r action where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching m d r action, or <code>null</code> if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the m d r action where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the m d r action that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of m d r actions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the m d r actions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the m d r actions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r actions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action, or <code>null</code> if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action, or <code>null</code> if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the m d r actions before and after the current m d r action in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param actionId the primary key of the current m d r action
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction[] findByUuid_C_PrevAndNext(
		long actionId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(actionId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the m d r actions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of m d r actions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the m d r actions where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @return the matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRuleGroupInstanceId(ruleGroupInstanceId);
	}

	/**
	* Returns a range of all the m d r actions where ruleGroupInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRuleGroupInstanceId(ruleGroupInstanceId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r actions where ruleGroupInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findByRuleGroupInstanceId(
		long ruleGroupInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRuleGroupInstanceId(ruleGroupInstanceId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction findByRuleGroupInstanceId_First(
		long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence()
				   .findByRuleGroupInstanceId_First(ruleGroupInstanceId,
			orderByComparator);
	}

	/**
	* Returns the first m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r action, or <code>null</code> if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByRuleGroupInstanceId_First(
		long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByRuleGroupInstanceId_First(ruleGroupInstanceId,
			orderByComparator);
	}

	/**
	* Returns the last m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction findByRuleGroupInstanceId_Last(
		long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence()
				   .findByRuleGroupInstanceId_Last(ruleGroupInstanceId,
			orderByComparator);
	}

	/**
	* Returns the last m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r action, or <code>null</code> if a matching m d r action could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByRuleGroupInstanceId_Last(
		long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByRuleGroupInstanceId_Last(ruleGroupInstanceId,
			orderByComparator);
	}

	/**
	* Returns the m d r actions before and after the current m d r action in the ordered set where ruleGroupInstanceId = &#63;.
	*
	* @param actionId the primary key of the current m d r action
	* @param ruleGroupInstanceId the rule group instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction[] findByRuleGroupInstanceId_PrevAndNext(
		long actionId, long ruleGroupInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence()
				   .findByRuleGroupInstanceId_PrevAndNext(actionId,
			ruleGroupInstanceId, orderByComparator);
	}

	/**
	* Removes all the m d r actions where ruleGroupInstanceId = &#63; from the database.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByRuleGroupInstanceId(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRuleGroupInstanceId(ruleGroupInstanceId);
	}

	/**
	* Returns the number of m d r actions where ruleGroupInstanceId = &#63;.
	*
	* @param ruleGroupInstanceId the rule group instance ID
	* @return the number of matching m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRuleGroupInstanceId(long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRuleGroupInstanceId(ruleGroupInstanceId);
	}

	/**
	* Caches the m d r action in the entity cache if it is enabled.
	*
	* @param mdrAction the m d r action
	*/
	public static void cacheResult(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction) {
		getPersistence().cacheResult(mdrAction);
	}

	/**
	* Caches the m d r actions in the entity cache if it is enabled.
	*
	* @param mdrActions the m d r actions
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> mdrActions) {
		getPersistence().cacheResult(mdrActions);
	}

	/**
	* Creates a new m d r action with the primary key. Does not add the m d r action to the database.
	*
	* @param actionId the primary key for the new m d r action
	* @return the new m d r action
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction create(
		long actionId) {
		return getPersistence().create(actionId);
	}

	/**
	* Removes the m d r action with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action that was removed
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction remove(
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence().remove(actionId);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRAction updateImpl(
		com.liferay.portlet.mobiledevicerules.model.MDRAction mdrAction)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mdrAction);
	}

	/**
	* Returns the m d r action with the primary key or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchActionException} if it could not be found.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchActionException if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction findByPrimaryKey(
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchActionException {
		return getPersistence().findByPrimaryKey(actionId);
	}

	/**
	* Returns the m d r action with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param actionId the primary key of the m d r action
	* @return the m d r action, or <code>null</code> if a m d r action with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRAction fetchByPrimaryKey(
		long actionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(actionId);
	}

	/**
	* Returns all the m d r actions.
	*
	* @return the m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the m d r actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @return the range of m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the m d r actions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRActionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r actions
	* @param end the upper bound of the range of m d r actions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRAction> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the m d r actions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of m d r actions.
	*
	* @return the number of m d r actions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MDRActionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MDRActionPersistence)PortalBeanLocatorUtil.locate(MDRActionPersistence.class.getName());

			ReferenceRegistry.registerReference(MDRActionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(MDRActionPersistence persistence) {
	}

	private static MDRActionPersistence _persistence;
}
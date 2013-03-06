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

import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance;

import java.util.List;

/**
 * The persistence utility for the m d r rule group instance service. This utility wraps {@link MDRRuleGroupInstancePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroupInstancePersistence
 * @see MDRRuleGroupInstancePersistenceImpl
 * @generated
 */
public class MDRRuleGroupInstanceUtil {
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
	public static void clearCache(MDRRuleGroupInstance mdrRuleGroupInstance) {
		getPersistence().clearCache(mdrRuleGroupInstance);
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
	public static List<MDRRuleGroupInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MDRRuleGroupInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MDRRuleGroupInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static MDRRuleGroupInstance update(
		MDRRuleGroupInstance mdrRuleGroupInstance) throws SystemException {
		return getPersistence().update(mdrRuleGroupInstance);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static MDRRuleGroupInstance update(
		MDRRuleGroupInstance mdrRuleGroupInstance, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(mdrRuleGroupInstance, serviceContext);
	}

	/**
	* Returns all the m d r rule group instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the m d r rule group instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where uuid = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance[] findByUuid_PrevAndNext(
		long ruleGroupInstanceId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByUuid_PrevAndNext(ruleGroupInstanceId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of m d r rule group instances where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the m d r rule group instance where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the m d r rule group instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the m d r rule group instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the m d r rule group instance where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the m d r rule group instance that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of m d r rule group instances where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance[] findByUuid_C_PrevAndNext(
		long ruleGroupInstanceId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(ruleGroupInstanceId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where uuid = &#63; and companyId = &#63; from the database.
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
	* Returns the number of m d r rule group instances where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the m d r rule group instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the m d r rule group instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where groupId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance[] findByGroupId_PrevAndNext(
		long ruleGroupInstanceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(ruleGroupInstanceId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching m d r rule group instances that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set of m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance[] filterFindByGroupId_PrevAndNext(
		long ruleGroupInstanceId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(ruleGroupInstanceId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of m d r rule group instances where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of m d r rule group instances that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching m d r rule group instances that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the m d r rule group instances where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRuleGroupId(ruleGroupId);
	}

	/**
	* Returns a range of all the m d r rule group instances where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByRuleGroupId(ruleGroupId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where ruleGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param ruleGroupId the rule group ID
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByRuleGroupId(
		long ruleGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByRuleGroupId(ruleGroupId, start, end, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByRuleGroupId_First(
		long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByRuleGroupId_First(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByRuleGroupId_First(
		long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByRuleGroupId_First(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByRuleGroupId_Last(
		long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByRuleGroupId_Last(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByRuleGroupId_Last(
		long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByRuleGroupId_Last(ruleGroupId, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where ruleGroupId = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param ruleGroupId the rule group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance[] findByRuleGroupId_PrevAndNext(
		long ruleGroupInstanceId, long ruleGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByRuleGroupId_PrevAndNext(ruleGroupInstanceId,
			ruleGroupId, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where ruleGroupId = &#63; from the database.
	*
	* @param ruleGroupId the rule group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByRuleGroupId(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByRuleGroupId(ruleGroupId);
	}

	/**
	* Returns the number of m d r rule group instances where ruleGroupId = &#63;.
	*
	* @param ruleGroupId the rule group ID
	* @return the number of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static int countByRuleGroupId(long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByRuleGroupId(ruleGroupId);
	}

	/**
	* Returns all the m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByC_C_Last(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance[] findByC_C_PrevAndNext(
		long ruleGroupInstanceId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByC_C_PrevAndNext(ruleGroupInstanceId, classNameId,
			classPK, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of m d r rule group instances where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns a range of all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_C_C(groupId, classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByG_C_C_First(
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the first m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_C_First(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the last m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_C_C_Last(groupId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance[] findByG_C_C_PrevAndNext(
		long ruleGroupInstanceId, long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .findByG_C_C_PrevAndNext(ruleGroupInstanceId, groupId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Returns all the m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching m d r rule group instances that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns a range of all the m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of matching m d r rule group instances that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_C(groupId, classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances that the user has permissions to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule group instances that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> filterFindByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_C_C(groupId, classNameId, classPK, start,
			end, orderByComparator);
	}

	/**
	* Returns the m d r rule group instances before and after the current m d r rule group instance in the ordered set of m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param ruleGroupInstanceId the primary key of the current m d r rule group instance
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance[] filterFindByG_C_C_PrevAndNext(
		long ruleGroupInstanceId, long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence()
				   .filterFindByG_C_C_PrevAndNext(ruleGroupInstanceId, groupId,
			classNameId, classPK, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_C_C(long groupId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of m d r rule group instances where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_C_C(long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the number of m d r rule group instances that the user has permission to view where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching m d r rule group instances that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_C_C(long groupId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_C_C(groupId, classNameId, classPK);
	}

	/**
	* Returns the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByC_C_R(
		long classNameId, long classPK, long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence().findByC_C_R(classNameId, classPK, ruleGroupId);
	}

	/**
	* Returns the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByC_C_R(
		long classNameId, long classPK, long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_C_R(classNameId, classPK, ruleGroupId);
	}

	/**
	* Returns the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching m d r rule group instance, or <code>null</code> if a matching m d r rule group instance could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByC_C_R(
		long classNameId, long classPK, long ruleGroupId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_C_R(classNameId, classPK, ruleGroupId,
			retrieveFromCache);
	}

	/**
	* Removes the m d r rule group instance where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the m d r rule group instance that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance removeByC_C_R(
		long classNameId, long classPK, long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence().removeByC_C_R(classNameId, classPK, ruleGroupId);
	}

	/**
	* Returns the number of m d r rule group instances where classNameId = &#63; and classPK = &#63; and ruleGroupId = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param ruleGroupId the rule group ID
	* @return the number of matching m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_C_R(long classNameId, long classPK,
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_C_R(classNameId, classPK, ruleGroupId);
	}

	/**
	* Caches the m d r rule group instance in the entity cache if it is enabled.
	*
	* @param mdrRuleGroupInstance the m d r rule group instance
	*/
	public static void cacheResult(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance mdrRuleGroupInstance) {
		getPersistence().cacheResult(mdrRuleGroupInstance);
	}

	/**
	* Caches the m d r rule group instances in the entity cache if it is enabled.
	*
	* @param mdrRuleGroupInstances the m d r rule group instances
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> mdrRuleGroupInstances) {
		getPersistence().cacheResult(mdrRuleGroupInstances);
	}

	/**
	* Creates a new m d r rule group instance with the primary key. Does not add the m d r rule group instance to the database.
	*
	* @param ruleGroupInstanceId the primary key for the new m d r rule group instance
	* @return the new m d r rule group instance
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance create(
		long ruleGroupInstanceId) {
		return getPersistence().create(ruleGroupInstanceId);
	}

	/**
	* Removes the m d r rule group instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance that was removed
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance remove(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence().remove(ruleGroupInstanceId);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance updateImpl(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance mdrRuleGroupInstance)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mdrRuleGroupInstance);
	}

	/**
	* Returns the m d r rule group instance with the primary key or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException} if it could not be found.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance findByPrimaryKey(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupInstanceException {
		return getPersistence().findByPrimaryKey(ruleGroupInstanceId);
	}

	/**
	* Returns the m d r rule group instance with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ruleGroupInstanceId the primary key of the m d r rule group instance
	* @return the m d r rule group instance, or <code>null</code> if a m d r rule group instance with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance fetchByPrimaryKey(
		long ruleGroupInstanceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(ruleGroupInstanceId);
	}

	/**
	* Returns all the m d r rule group instances.
	*
	* @return the m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the m d r rule group instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @return the range of m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule group instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule group instances
	* @param end the upper bound of the range of m d r rule group instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the m d r rule group instances from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of m d r rule group instances.
	*
	* @return the number of m d r rule group instances
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MDRRuleGroupInstancePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MDRRuleGroupInstancePersistence)PortalBeanLocatorUtil.locate(MDRRuleGroupInstancePersistence.class.getName());

			ReferenceRegistry.registerReference(MDRRuleGroupInstanceUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(MDRRuleGroupInstancePersistence persistence) {
	}

	private static MDRRuleGroupInstancePersistence _persistence;
}
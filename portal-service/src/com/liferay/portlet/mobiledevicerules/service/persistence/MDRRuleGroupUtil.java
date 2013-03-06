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

import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;

import java.util.List;

/**
 * The persistence utility for the m d r rule group service. This utility wraps {@link MDRRuleGroupPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRuleGroupPersistence
 * @see MDRRuleGroupPersistenceImpl
 * @generated
 */
public class MDRRuleGroupUtil {
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
	public static void clearCache(MDRRuleGroup mdrRuleGroup) {
		getPersistence().clearCache(mdrRuleGroup);
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
	public static List<MDRRuleGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<MDRRuleGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<MDRRuleGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static MDRRuleGroup update(MDRRuleGroup mdrRuleGroup)
		throws SystemException {
		return getPersistence().update(mdrRuleGroup);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static MDRRuleGroup update(MDRRuleGroup mdrRuleGroup,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(mdrRuleGroup, serviceContext);
	}

	/**
	* Returns all the m d r rule groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the m d r rule groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @return the range of matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns the first m d r rule group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first m d r rule group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last m d r rule group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last m d r rule group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the m d r rule groups before and after the current m d r rule group in the ordered set where uuid = &#63;.
	*
	* @param ruleGroupId the primary key of the current m d r rule group
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup[] findByUuid_PrevAndNext(
		long ruleGroupId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence()
				   .findByUuid_PrevAndNext(ruleGroupId, uuid, orderByComparator);
	}

	/**
	* Removes all the m d r rule groups where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of m d r rule groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns the m d r rule group where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence().findByUUID_G(uuid, groupId);
	}

	/**
	* Returns the m d r rule group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId);
	}

	/**
	* Returns the m d r rule group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUUID_G(uuid, groupId, retrieveFromCache);
	}

	/**
	* Removes the m d r rule group where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the m d r rule group that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence().removeByUUID_G(uuid, groupId);
	}

	/**
	* Returns the number of m d r rule groups where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUUID_G(uuid, groupId);
	}

	/**
	* Returns all the m d r rule groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByUuid_C(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the m d r rule groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @return the range of matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first m d r rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first m d r rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the m d r rule groups before and after the current m d r rule group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param ruleGroupId the primary key of the current m d r rule group
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup[] findByUuid_C_PrevAndNext(
		long ruleGroupId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(ruleGroupId, uuid, companyId,
			orderByComparator);
	}

	/**
	* Removes all the m d r rule groups where uuid = &#63; and companyId = &#63; from the database.
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
	* Returns the number of m d r rule groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUuid_C(java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the m d r rule groups where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the m d r rule groups where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @return the range of matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule groups where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first m d r rule group in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first m d r rule group in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last m d r rule group in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching m d r rule group, or <code>null</code> if a matching m d r rule group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the m d r rule groups before and after the current m d r rule group in the ordered set where groupId = &#63;.
	*
	* @param ruleGroupId the primary key of the current m d r rule group
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup[] findByGroupId_PrevAndNext(
		long ruleGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(ruleGroupId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the m d r rule groups that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching m d r rule groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the m d r rule groups that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @return the range of matching m d r rule groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule groups that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching m d r rule groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the m d r rule groups before and after the current m d r rule group in the ordered set of m d r rule groups that the user has permission to view where groupId = &#63;.
	*
	* @param ruleGroupId the primary key of the current m d r rule group
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup[] filterFindByGroupId_PrevAndNext(
		long ruleGroupId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(ruleGroupId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the m d r rule groups where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of m d r rule groups where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of m d r rule groups that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching m d r rule groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Caches the m d r rule group in the entity cache if it is enabled.
	*
	* @param mdrRuleGroup the m d r rule group
	*/
	public static void cacheResult(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup mdrRuleGroup) {
		getPersistence().cacheResult(mdrRuleGroup);
	}

	/**
	* Caches the m d r rule groups in the entity cache if it is enabled.
	*
	* @param mdrRuleGroups the m d r rule groups
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> mdrRuleGroups) {
		getPersistence().cacheResult(mdrRuleGroups);
	}

	/**
	* Creates a new m d r rule group with the primary key. Does not add the m d r rule group to the database.
	*
	* @param ruleGroupId the primary key for the new m d r rule group
	* @return the new m d r rule group
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup create(
		long ruleGroupId) {
		return getPersistence().create(ruleGroupId);
	}

	/**
	* Removes the m d r rule group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ruleGroupId the primary key of the m d r rule group
	* @return the m d r rule group that was removed
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup remove(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence().remove(ruleGroupId);
	}

	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup updateImpl(
		com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup mdrRuleGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(mdrRuleGroup);
	}

	/**
	* Returns the m d r rule group with the primary key or throws a {@link com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException} if it could not be found.
	*
	* @param ruleGroupId the primary key of the m d r rule group
	* @return the m d r rule group
	* @throws com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException if a m d r rule group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup findByPrimaryKey(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException {
		return getPersistence().findByPrimaryKey(ruleGroupId);
	}

	/**
	* Returns the m d r rule group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param ruleGroupId the primary key of the m d r rule group
	* @return the m d r rule group, or <code>null</code> if a m d r rule group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup fetchByPrimaryKey(
		long ruleGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(ruleGroupId);
	}

	/**
	* Returns all the m d r rule groups.
	*
	* @return the m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the m d r rule groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @return the range of m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the m d r rule groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.mobiledevicerules.model.impl.MDRRuleGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of m d r rule groups
	* @param end the upper bound of the range of m d r rule groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the m d r rule groups from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of m d r rule groups.
	*
	* @return the number of m d r rule groups
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static MDRRuleGroupPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (MDRRuleGroupPersistence)PortalBeanLocatorUtil.locate(MDRRuleGroupPersistence.class.getName());

			ReferenceRegistry.registerReference(MDRRuleGroupUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(MDRRuleGroupPersistence persistence) {
	}

	private static MDRRuleGroupPersistence _persistence;
}
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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;

import java.util.List;

/**
 * The persistence utility for the s c framework version service. This utility wraps {@link SCFrameworkVersionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SCFrameworkVersionPersistence
 * @see SCFrameworkVersionPersistenceImpl
 * @generated
 */
public class SCFrameworkVersionUtil {
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
	public static void clearCache(SCFrameworkVersion scFrameworkVersion) {
		getPersistence().clearCache(scFrameworkVersion);
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
	public static List<SCFrameworkVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<SCFrameworkVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<SCFrameworkVersion> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static SCFrameworkVersion update(
		SCFrameworkVersion scFrameworkVersion) throws SystemException {
		return getPersistence().update(scFrameworkVersion);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static SCFrameworkVersion update(
		SCFrameworkVersion scFrameworkVersion, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(scFrameworkVersion, serviceContext);
	}

	/**
	* Returns all the s c framework versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId);
	}

	/**
	* Returns a range of all the s c framework versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the s c framework versions where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the first s c framework version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first s c framework version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last s c framework version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last s c framework version in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the s c framework versions before and after the current s c framework version in the ordered set where groupId = &#63;.
	*
	* @param frameworkVersionId the primary key of the current s c framework version
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] findByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(frameworkVersionId, groupId,
			orderByComparator);
	}

	/**
	* Returns all the s c framework versions that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching s c framework versions that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> filterFindByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId);
	}

	/**
	* Returns a range of all the s c framework versions that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of matching s c framework versions that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> filterFindByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByGroupId(groupId, start, end);
	}

	/**
	* Returns an ordered range of all the s c framework versions that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c framework versions that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> filterFindByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByGroupId(groupId, start, end, orderByComparator);
	}

	/**
	* Returns the s c framework versions before and after the current s c framework version in the ordered set of s c framework versions that the user has permission to view where groupId = &#63;.
	*
	* @param frameworkVersionId the primary key of the current s c framework version
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] filterFindByGroupId_PrevAndNext(
		long frameworkVersionId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence()
				   .filterFindByGroupId_PrevAndNext(frameworkVersionId,
			groupId, orderByComparator);
	}

	/**
	* Removes all the s c framework versions where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of s c framework versions where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the number of s c framework versions that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching s c framework versions that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByGroupId(groupId);
	}

	/**
	* Returns all the s c framework versions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the s c framework versions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the s c framework versions where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first s c framework version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first s c framework version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last s c framework version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last s c framework version in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the s c framework versions before and after the current s c framework version in the ordered set where companyId = &#63;.
	*
	* @param frameworkVersionId the primary key of the current s c framework version
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] findByCompanyId_PrevAndNext(
		long frameworkVersionId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(frameworkVersionId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the s c framework versions where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of s c framework versions where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the s c framework versions where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByG_A(
		long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_A(groupId, active);
	}

	/**
	* Returns a range of all the s c framework versions where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByG_A(
		long groupId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the s c framework versions where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findByG_A(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByG_A(groupId, active, start, end, orderByComparator);
	}

	/**
	* Returns the first s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByG_A_First(
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence()
				   .findByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the first s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchByG_A_First(
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_A_First(groupId, active, orderByComparator);
	}

	/**
	* Returns the last s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByG_A_Last(
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence()
				   .findByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the last s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching s c framework version, or <code>null</code> if a matching s c framework version could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchByG_A_Last(
		long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByG_A_Last(groupId, active, orderByComparator);
	}

	/**
	* Returns the s c framework versions before and after the current s c framework version in the ordered set where groupId = &#63; and active = &#63;.
	*
	* @param frameworkVersionId the primary key of the current s c framework version
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] findByG_A_PrevAndNext(
		long frameworkVersionId, long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence()
				   .findByG_A_PrevAndNext(frameworkVersionId, groupId, active,
			orderByComparator);
	}

	/**
	* Returns all the s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the matching s c framework versions that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> filterFindByG_A(
		long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_A(groupId, active);
	}

	/**
	* Returns a range of all the s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of matching s c framework versions that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> filterFindByG_A(
		long groupId, boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByG_A(groupId, active, start, end);
	}

	/**
	* Returns an ordered range of all the s c framework versions that the user has permissions to view where groupId = &#63; and active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param active the active
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching s c framework versions that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> filterFindByG_A(
		long groupId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByG_A(groupId, active, start, end,
			orderByComparator);
	}

	/**
	* Returns the s c framework versions before and after the current s c framework version in the ordered set of s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param frameworkVersionId the primary key of the current s c framework version
	* @param groupId the group ID
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion[] filterFindByG_A_PrevAndNext(
		long frameworkVersionId, long groupId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence()
				   .filterFindByG_A_PrevAndNext(frameworkVersionId, groupId,
			active, orderByComparator);
	}

	/**
	* Removes all the s c framework versions where groupId = &#63; and active = &#63; from the database.
	*
	* @param groupId the group ID
	* @param active the active
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByG_A(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByG_A(groupId, active);
	}

	/**
	* Returns the number of s c framework versions where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByG_A(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByG_A(groupId, active);
	}

	/**
	* Returns the number of s c framework versions that the user has permission to view where groupId = &#63; and active = &#63;.
	*
	* @param groupId the group ID
	* @param active the active
	* @return the number of matching s c framework versions that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByG_A(long groupId, boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByG_A(groupId, active);
	}

	/**
	* Caches the s c framework version in the entity cache if it is enabled.
	*
	* @param scFrameworkVersion the s c framework version
	*/
	public static void cacheResult(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion) {
		getPersistence().cacheResult(scFrameworkVersion);
	}

	/**
	* Caches the s c framework versions in the entity cache if it is enabled.
	*
	* @param scFrameworkVersions the s c framework versions
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> scFrameworkVersions) {
		getPersistence().cacheResult(scFrameworkVersions);
	}

	/**
	* Creates a new s c framework version with the primary key. Does not add the s c framework version to the database.
	*
	* @param frameworkVersionId the primary key for the new s c framework version
	* @return the new s c framework version
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion create(
		long frameworkVersionId) {
		return getPersistence().create(frameworkVersionId);
	}

	/**
	* Removes the s c framework version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version that was removed
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion remove(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().remove(frameworkVersionId);
	}

	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateImpl(
		com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion scFrameworkVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(scFrameworkVersion);
	}

	/**
	* Returns the s c framework version with the primary key or throws a {@link com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException} if it could not be found.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version
	* @throws com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion findByPrimaryKey(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException {
		return getPersistence().findByPrimaryKey(frameworkVersionId);
	}

	/**
	* Returns the s c framework version with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param frameworkVersionId the primary key of the s c framework version
	* @return the s c framework version, or <code>null</code> if a s c framework version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion fetchByPrimaryKey(
		long frameworkVersionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(frameworkVersionId);
	}

	/**
	* Returns all the s c framework versions.
	*
	* @return the s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the s c framework versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the s c framework versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the s c framework versions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of s c framework versions.
	*
	* @return the number of s c framework versions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	/**
	* Returns all the s c product versions associated with the s c framework version.
	*
	* @param pk the primary key of the s c framework version
	* @return the s c product versions associated with the s c framework version
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCProductVersions(pk);
	}

	/**
	* Returns a range of all the s c product versions associated with the s c framework version.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c framework version
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @return the range of s c product versions associated with the s c framework version
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCProductVersions(pk, start, end);
	}

	/**
	* Returns an ordered range of all the s c product versions associated with the s c framework version.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.softwarecatalog.model.impl.SCFrameworkVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the s c framework version
	* @param start the lower bound of the range of s c framework versions
	* @param end the upper bound of the range of s c framework versions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of s c product versions associated with the s c framework version
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> getSCProductVersions(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .getSCProductVersions(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of s c product versions associated with the s c framework version.
	*
	* @param pk the primary key of the s c framework version
	* @return the number of s c product versions associated with the s c framework version
	* @throws SystemException if a system exception occurred
	*/
	public static int getSCProductVersionsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getSCProductVersionsSize(pk);
	}

	/**
	* Returns <code>true</code> if the s c product version is associated with the s c framework version.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPK the primary key of the s c product version
	* @return <code>true</code> if the s c product version is associated with the s c framework version; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsSCProductVersion(long pk,
		long scProductVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsSCProductVersion(pk, scProductVersionPK);
	}

	/**
	* Returns <code>true</code> if the s c framework version has any s c product versions associated with it.
	*
	* @param pk the primary key of the s c framework version to check for associations with s c product versions
	* @return <code>true</code> if the s c framework version has any s c product versions associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsSCProductVersions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsSCProductVersions(pk);
	}

	/**
	* Adds an association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPK the primary key of the s c product version
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductVersion(long pk, long scProductVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCProductVersion(pk, scProductVersionPK);
	}

	/**
	* Adds an association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersion the s c product version
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCProductVersion(pk, scProductVersion);
	}

	/**
	* Adds an association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPKs the primary keys of the s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductVersions(long pk, long[] scProductVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCProductVersions(pk, scProductVersionPKs);
	}

	/**
	* Adds an association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersions the s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public static void addSCProductVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addSCProductVersions(pk, scProductVersions);
	}

	/**
	* Clears all associations between the s c framework version and its s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version to clear the associated s c product versions from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearSCProductVersions(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearSCProductVersions(pk);
	}

	/**
	* Removes the association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPK the primary key of the s c product version
	* @throws SystemException if a system exception occurred
	*/
	public static void removeSCProductVersion(long pk, long scProductVersionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCProductVersion(pk, scProductVersionPK);
	}

	/**
	* Removes the association between the s c framework version and the s c product version. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersion the s c product version
	* @throws SystemException if a system exception occurred
	*/
	public static void removeSCProductVersion(long pk,
		com.liferay.portlet.softwarecatalog.model.SCProductVersion scProductVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCProductVersion(pk, scProductVersion);
	}

	/**
	* Removes the association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPKs the primary keys of the s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public static void removeSCProductVersions(long pk,
		long[] scProductVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCProductVersions(pk, scProductVersionPKs);
	}

	/**
	* Removes the association between the s c framework version and the s c product versions. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersions the s c product versions
	* @throws SystemException if a system exception occurred
	*/
	public static void removeSCProductVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeSCProductVersions(pk, scProductVersions);
	}

	/**
	* Sets the s c product versions associated with the s c framework version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersionPKs the primary keys of the s c product versions to be associated with the s c framework version
	* @throws SystemException if a system exception occurred
	*/
	public static void setSCProductVersions(long pk, long[] scProductVersionPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setSCProductVersions(pk, scProductVersionPKs);
	}

	/**
	* Sets the s c product versions associated with the s c framework version, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the s c framework version
	* @param scProductVersions the s c product versions to be associated with the s c framework version
	* @throws SystemException if a system exception occurred
	*/
	public static void setSCProductVersions(long pk,
		java.util.List<com.liferay.portlet.softwarecatalog.model.SCProductVersion> scProductVersions)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setSCProductVersions(pk, scProductVersions);
	}

	public static SCFrameworkVersionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SCFrameworkVersionPersistence)PortalBeanLocatorUtil.locate(SCFrameworkVersionPersistence.class.getName());

			ReferenceRegistry.registerReference(SCFrameworkVersionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(SCFrameworkVersionPersistence persistence) {
	}

	private static SCFrameworkVersionPersistence _persistence;
}
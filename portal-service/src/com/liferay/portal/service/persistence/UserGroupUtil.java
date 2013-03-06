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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * The persistence utility for the user group service. This utility wraps {@link UserGroupPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupPersistence
 * @see UserGroupPersistenceImpl
 * @generated
 */
public class UserGroupUtil {
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
	public static void clearCache(UserGroup userGroup) {
		getPersistence().clearCache(userGroup);
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
	public static List<UserGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<UserGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<UserGroup> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static UserGroup update(UserGroup userGroup)
		throws SystemException {
		return getPersistence().update(userGroup);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static UserGroup update(UserGroup userGroup,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(userGroup, serviceContext);
	}

	/**
	* Returns all the user groups where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the user groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the user groups where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first user group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first user group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group, or <code>null</code> if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last user group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last user group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group, or <code>null</code> if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the user groups before and after the current user group in the ordered set where companyId = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup[] findByCompanyId_PrevAndNext(
		long userGroupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(userGroupId, companyId,
			orderByComparator);
	}

	/**
	* Returns all the user groups that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> filterFindByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByCompanyId(companyId);
	}

	/**
	* Returns a range of all the user groups that the user has permission to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> filterFindByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the user groups that the user has permissions to view where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByCompanyId(companyId, start, end,
			orderByComparator);
	}

	/**
	* Returns the user groups before and after the current user group in the ordered set of user groups that the user has permission to view where companyId = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup[] filterFindByCompanyId_PrevAndNext(
		long userGroupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByCompanyId_PrevAndNext(userGroupId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the user groups where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of user groups where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns the number of user groups that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByCompanyId(companyId);
	}

	/**
	* Returns all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @return the matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_P(companyId, parentUserGroupId);
	}

	/**
	* Returns a range of all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P(companyId, parentUserGroupId, start, end);
	}

	/**
	* Returns an ordered range of all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P(companyId, parentUserGroupId, start, end,
			orderByComparator);
	}

	/**
	* Returns the first user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup findByC_P_First(
		long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P_First(companyId, parentUserGroupId,
			orderByComparator);
	}

	/**
	* Returns the first user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group, or <code>null</code> if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup fetchByC_P_First(
		long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_P_First(companyId, parentUserGroupId,
			orderByComparator);
	}

	/**
	* Returns the last user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup findByC_P_Last(
		long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P_Last(companyId, parentUserGroupId,
			orderByComparator);
	}

	/**
	* Returns the last user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group, or <code>null</code> if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup fetchByC_P_Last(
		long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByC_P_Last(companyId, parentUserGroupId,
			orderByComparator);
	}

	/**
	* Returns the user groups before and after the current user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup[] findByC_P_PrevAndNext(
		long userGroupId, long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByC_P_PrevAndNext(userGroupId, companyId,
			parentUserGroupId, orderByComparator);
	}

	/**
	* Returns all the user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @return the matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> filterFindByC_P(
		long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByC_P(companyId, parentUserGroupId);
	}

	/**
	* Returns a range of all the user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> filterFindByC_P(
		long companyId, long parentUserGroupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByC_P(companyId, parentUserGroupId, start, end);
	}

	/**
	* Returns an ordered range of all the user groups that the user has permissions to view where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> filterFindByC_P(
		long companyId, long parentUserGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByC_P(companyId, parentUserGroupId, start, end,
			orderByComparator);
	}

	/**
	* Returns the user groups before and after the current user group in the ordered set of user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup[] filterFindByC_P_PrevAndNext(
		long userGroupId, long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByC_P_PrevAndNext(userGroupId, companyId,
			parentUserGroupId, orderByComparator);
	}

	/**
	* Removes all the user groups where companyId = &#63; and parentUserGroupId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByC_P(long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByC_P(companyId, parentUserGroupId);
	}

	/**
	* Returns the number of user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @return the number of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_P(long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_P(companyId, parentUserGroupId);
	}

	/**
	* Returns the number of user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @return the number of matching user groups that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByC_P(long companyId, long parentUserGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByC_P(companyId, parentUserGroupId);
	}

	/**
	* Returns the user group where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchUserGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByC_N(companyId, name);
	}

	/**
	* Returns the user group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching user group, or <code>null</code> if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup fetchByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_N(companyId, name);
	}

	/**
	* Returns the user group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user group, or <code>null</code> if a matching user group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup fetchByC_N(
		long companyId, java.lang.String name, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByC_N(companyId, name, retrieveFromCache);
	}

	/**
	* Removes the user group where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the user group that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup removeByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().removeByC_N(companyId, name);
	}

	/**
	* Returns the number of user groups where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching user groups
	* @throws SystemException if a system exception occurred
	*/
	public static int countByC_N(long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByC_N(companyId, name);
	}

	/**
	* Caches the user group in the entity cache if it is enabled.
	*
	* @param userGroup the user group
	*/
	public static void cacheResult(com.liferay.portal.model.UserGroup userGroup) {
		getPersistence().cacheResult(userGroup);
	}

	/**
	* Caches the user groups in the entity cache if it is enabled.
	*
	* @param userGroups the user groups
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups) {
		getPersistence().cacheResult(userGroups);
	}

	/**
	* Creates a new user group with the primary key. Does not add the user group to the database.
	*
	* @param userGroupId the primary key for the new user group
	* @return the new user group
	*/
	public static com.liferay.portal.model.UserGroup create(long userGroupId) {
		return getPersistence().create(userGroupId);
	}

	/**
	* Removes the user group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupId the primary key of the user group
	* @return the user group that was removed
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup remove(long userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().remove(userGroupId);
	}

	public static com.liferay.portal.model.UserGroup updateImpl(
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(userGroup);
	}

	/**
	* Returns the user group with the primary key or throws a {@link com.liferay.portal.NoSuchUserGroupException} if it could not be found.
	*
	* @param userGroupId the primary key of the user group
	* @return the user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup findByPrimaryKey(
		long userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException,
			com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPrimaryKey(userGroupId);
	}

	/**
	* Returns the user group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userGroupId the primary key of the user group
	* @return the user group, or <code>null</code> if a user group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.UserGroup fetchByPrimaryKey(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(userGroupId);
	}

	/**
	* Returns all the user groups.
	*
	* @return the user groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the user groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of user groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the user groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of user groups
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the user groups from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of user groups.
	*
	* @return the number of user groups
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	/**
	* Returns all the groups associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the groups associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk);
	}

	/**
	* Returns a range of all the groups associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user group
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of groups associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk, start, end);
	}

	/**
	* Returns an ordered range of all the groups associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user group
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of groups associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroups(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of groups associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the number of groups associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static int getGroupsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getGroupsSize(pk);
	}

	/**
	* Returns <code>true</code> if the group is associated with the user group.
	*
	* @param pk the primary key of the user group
	* @param groupPK the primary key of the group
	* @return <code>true</code> if the group is associated with the user group; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsGroup(pk, groupPK);
	}

	/**
	* Returns <code>true</code> if the user group has any groups associated with it.
	*
	* @param pk the primary key of the user group to check for associations with groups
	* @return <code>true</code> if the user group has any groups associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsGroups(pk);
	}

	/**
	* Adds an association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPK the primary key of the group
	* @throws SystemException if a system exception occurred
	*/
	public static void addGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroup(pk, groupPK);
	}

	/**
	* Adds an association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param group the group
	* @throws SystemException if a system exception occurred
	*/
	public static void addGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroup(pk, group);
	}

	/**
	* Adds an association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPKs the primary keys of the groups
	* @throws SystemException if a system exception occurred
	*/
	public static void addGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroups(pk, groupPKs);
	}

	/**
	* Adds an association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groups the groups
	* @throws SystemException if a system exception occurred
	*/
	public static void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addGroups(pk, groups);
	}

	/**
	* Clears all associations between the user group and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to clear the associated groups from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearGroups(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearGroups(pk);
	}

	/**
	* Removes the association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPK the primary key of the group
	* @throws SystemException if a system exception occurred
	*/
	public static void removeGroup(long pk, long groupPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroup(pk, groupPK);
	}

	/**
	* Removes the association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param group the group
	* @throws SystemException if a system exception occurred
	*/
	public static void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroup(pk, group);
	}

	/**
	* Removes the association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPKs the primary keys of the groups
	* @throws SystemException if a system exception occurred
	*/
	public static void removeGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroups(pk, groupPKs);
	}

	/**
	* Removes the association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groups the groups
	* @throws SystemException if a system exception occurred
	*/
	public static void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeGroups(pk, groups);
	}

	/**
	* Sets the groups associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPKs the primary keys of the groups to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static void setGroups(long pk, long[] groupPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setGroups(pk, groupPKs);
	}

	/**
	* Sets the groups associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groups the groups to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setGroups(pk, groups);
	}

	/**
	* Returns all the teams associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the teams associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Team> getTeams(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getTeams(pk);
	}

	/**
	* Returns a range of all the teams associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user group
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of teams associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Team> getTeams(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getTeams(pk, start, end);
	}

	/**
	* Returns an ordered range of all the teams associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user group
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of teams associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Team> getTeams(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getTeams(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of teams associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the number of teams associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static int getTeamsSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getTeamsSize(pk);
	}

	/**
	* Returns <code>true</code> if the team is associated with the user group.
	*
	* @param pk the primary key of the user group
	* @param teamPK the primary key of the team
	* @return <code>true</code> if the team is associated with the user group; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsTeam(pk, teamPK);
	}

	/**
	* Returns <code>true</code> if the user group has any teams associated with it.
	*
	* @param pk the primary key of the user group to check for associations with teams
	* @return <code>true</code> if the user group has any teams associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsTeams(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsTeams(pk);
	}

	/**
	* Adds an association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPK the primary key of the team
	* @throws SystemException if a system exception occurred
	*/
	public static void addTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addTeam(pk, teamPK);
	}

	/**
	* Adds an association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param team the team
	* @throws SystemException if a system exception occurred
	*/
	public static void addTeam(long pk, com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addTeam(pk, team);
	}

	/**
	* Adds an association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPKs the primary keys of the teams
	* @throws SystemException if a system exception occurred
	*/
	public static void addTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addTeams(pk, teamPKs);
	}

	/**
	* Adds an association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teams the teams
	* @throws SystemException if a system exception occurred
	*/
	public static void addTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addTeams(pk, teams);
	}

	/**
	* Clears all associations between the user group and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to clear the associated teams from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearTeams(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearTeams(pk);
	}

	/**
	* Removes the association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPK the primary key of the team
	* @throws SystemException if a system exception occurred
	*/
	public static void removeTeam(long pk, long teamPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeTeam(pk, teamPK);
	}

	/**
	* Removes the association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param team the team
	* @throws SystemException if a system exception occurred
	*/
	public static void removeTeam(long pk, com.liferay.portal.model.Team team)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeTeam(pk, team);
	}

	/**
	* Removes the association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPKs the primary keys of the teams
	* @throws SystemException if a system exception occurred
	*/
	public static void removeTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeTeams(pk, teamPKs);
	}

	/**
	* Removes the association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teams the teams
	* @throws SystemException if a system exception occurred
	*/
	public static void removeTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeTeams(pk, teams);
	}

	/**
	* Sets the teams associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPKs the primary keys of the teams to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static void setTeams(long pk, long[] teamPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setTeams(pk, teamPKs);
	}

	/**
	* Sets the teams associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teams the teams to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static void setTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setTeams(pk, teams);
	}

	/**
	* Returns all the users associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the users associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk);
	}

	/**
	* Returns a range of all the users associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user group
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of users associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk, start, end);
	}

	/**
	* Returns an ordered range of all the users associated with the user group.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param pk the primary key of the user group
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of users associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.User> getUsers(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsers(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of users associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the number of users associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static int getUsersSize(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().getUsersSize(pk);
	}

	/**
	* Returns <code>true</code> if the user is associated with the user group.
	*
	* @param pk the primary key of the user group
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the user group; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsUser(pk, userPK);
	}

	/**
	* Returns <code>true</code> if the user group has any users associated with it.
	*
	* @param pk the primary key of the user group to check for associations with users
	* @return <code>true</code> if the user group has any users associated with it; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public static boolean containsUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().containsUsers(pk);
	}

	/**
	* Adds an association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPK the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public static void addUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUser(pk, userPK);
	}

	/**
	* Adds an association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param user the user
	* @throws SystemException if a system exception occurred
	*/
	public static void addUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUser(pk, user);
	}

	/**
	* Adds an association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPKs the primary keys of the users
	* @throws SystemException if a system exception occurred
	*/
	public static void addUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUsers(pk, userPKs);
	}

	/**
	* Adds an association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param users the users
	* @throws SystemException if a system exception occurred
	*/
	public static void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().addUsers(pk, users);
	}

	/**
	* Clears all associations between the user group and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to clear the associated users from
	* @throws SystemException if a system exception occurred
	*/
	public static void clearUsers(long pk)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().clearUsers(pk);
	}

	/**
	* Removes the association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPK the primary key of the user
	* @throws SystemException if a system exception occurred
	*/
	public static void removeUser(long pk, long userPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUser(pk, userPK);
	}

	/**
	* Removes the association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param user the user
	* @throws SystemException if a system exception occurred
	*/
	public static void removeUser(long pk, com.liferay.portal.model.User user)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUser(pk, user);
	}

	/**
	* Removes the association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPKs the primary keys of the users
	* @throws SystemException if a system exception occurred
	*/
	public static void removeUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUsers(pk, userPKs);
	}

	/**
	* Removes the association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param users the users
	* @throws SystemException if a system exception occurred
	*/
	public static void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeUsers(pk, users);
	}

	/**
	* Sets the users associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPKs the primary keys of the users to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static void setUsers(long pk, long[] userPKs)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setUsers(pk, userPKs);
	}

	/**
	* Sets the users associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param users the users to be associated with the user group
	* @throws SystemException if a system exception occurred
	*/
	public static void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().setUsers(pk, users);
	}

	public static UserGroupPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (UserGroupPersistence)PortalBeanLocatorUtil.locate(UserGroupPersistence.class.getName());

			ReferenceRegistry.registerReference(UserGroupUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(UserGroupPersistence persistence) {
	}

	private static UserGroupPersistence _persistence;
}
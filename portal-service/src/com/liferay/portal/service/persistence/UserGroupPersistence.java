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

import com.liferay.portal.model.UserGroup;

/**
 * The persistence interface for the user group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupPersistenceImpl
 * @see UserGroupUtil
 * @generated
 */
@ProviderType
public interface UserGroupPersistence extends BasePersistence<UserGroup> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserGroupUtil} to access the user group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the user groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching user groups
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the user groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of matching user groups
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the user groups where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first user group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the first user group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last user group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the last user group in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user groups before and after the current user group in the ordered set where uuid = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup[] findByUuid_PrevAndNext(
		long userGroupId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns all the user groups that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching user groups that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the user groups that the user has permission to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of matching user groups that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the user groups that the user has permissions to view where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user groups before and after the current user group in the ordered set of user groups that the user has permission to view where uuid = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup[] filterFindByUuid_PrevAndNext(
		long userGroupId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Removes all the user groups where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of user groups where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching user groups
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the number of user groups that the user has permission to view where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching user groups that the user has permission to view
	*/
	public int filterCountByUuid(java.lang.String uuid);

	/**
	* Returns all the user groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching user groups
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the user groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of matching user groups
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the user groups where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first user group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the first user group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last user group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the last user group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user groups before and after the current user group in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup[] findByUuid_C_PrevAndNext(
		long userGroupId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns all the user groups that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching user groups that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the user groups that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @return the range of matching user groups that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the user groups that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of user groups
	* @param end the upper bound of the range of user groups (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching user groups that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user groups before and after the current user group in the ordered set of user groups that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup[] filterFindByUuid_C_PrevAndNext(
		long userGroupId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Removes all the user groups where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of user groups where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching user groups
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of user groups that the user has permission to view where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching user groups that the user has permission to view
	*/
	public int filterCountByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the user groups where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching user groups
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId);

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
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId, int start, int end);

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
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first user group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the first user group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last user group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the last user group in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user groups before and after the current user group in the ordered set where companyId = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup[] findByCompanyId_PrevAndNext(
		long userGroupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns all the user groups that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching user groups that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByCompanyId(
		long companyId);

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
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByCompanyId(
		long companyId, int start, int end);

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
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user groups before and after the current user group in the ordered set of user groups that the user has permission to view where companyId = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup[] filterFindByCompanyId_PrevAndNext(
		long userGroupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Removes all the user groups where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of user groups where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching user groups
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the number of user groups that the user has permission to view where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching user groups that the user has permission to view
	*/
	public int filterCountByCompanyId(long companyId);

	/**
	* Returns all the user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @return the matching user groups
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId);

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
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId, int start, int end);

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
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findByC_P(
		long companyId, long parentUserGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the first user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup findByC_P_First(long companyId,
		long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the first user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByC_P_First(long companyId,
		long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the last user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup findByC_P_Last(long companyId,
		long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the last user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByC_P_Last(long companyId,
		long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user groups before and after the current user group in the ordered set where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup[] findByC_P_PrevAndNext(
		long userGroupId, long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns all the user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @return the matching user groups that the user has permission to view
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByC_P(
		long companyId, long parentUserGroupId);

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
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByC_P(
		long companyId, long parentUserGroupId, int start, int end);

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
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> filterFindByC_P(
		long companyId, long parentUserGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the user groups before and after the current user group in the ordered set of user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param userGroupId the primary key of the current user group
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup[] filterFindByC_P_PrevAndNext(
		long userGroupId, long companyId, long parentUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Removes all the user groups where companyId = &#63; and parentUserGroupId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	*/
	public void removeByC_P(long companyId, long parentUserGroupId);

	/**
	* Returns the number of user groups where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @return the number of matching user groups
	*/
	public int countByC_P(long companyId, long parentUserGroupId);

	/**
	* Returns the number of user groups that the user has permission to view where companyId = &#63; and parentUserGroupId = &#63;.
	*
	* @param companyId the company ID
	* @param parentUserGroupId the parent user group ID
	* @return the number of matching user groups that the user has permission to view
	*/
	public int filterCountByC_P(long companyId, long parentUserGroupId);

	/**
	* Returns the user group where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchUserGroupException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup findByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the user group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByC_N(long companyId,
		java.lang.String name);

	/**
	* Returns the user group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByC_N(long companyId,
		java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the user group where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the user group that was removed
	*/
	public com.liferay.portal.model.UserGroup removeByC_N(long companyId,
		java.lang.String name)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the number of user groups where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching user groups
	*/
	public int countByC_N(long companyId, java.lang.String name);

	/**
	* Caches the user group in the entity cache if it is enabled.
	*
	* @param userGroup the user group
	*/
	public void cacheResult(com.liferay.portal.model.UserGroup userGroup);

	/**
	* Caches the user groups in the entity cache if it is enabled.
	*
	* @param userGroups the user groups
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups);

	/**
	* Creates a new user group with the primary key. Does not add the user group to the database.
	*
	* @param userGroupId the primary key for the new user group
	* @return the new user group
	*/
	public com.liferay.portal.model.UserGroup create(long userGroupId);

	/**
	* Removes the user group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupId the primary key of the user group
	* @return the user group that was removed
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup remove(long userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException;

	public com.liferay.portal.model.UserGroup updateImpl(
		com.liferay.portal.model.UserGroup userGroup);

	/**
	* Returns the user group with the primary key or throws a {@link com.liferay.portal.NoSuchUserGroupException} if it could not be found.
	*
	* @param userGroupId the primary key of the user group
	* @return the user group
	* @throws com.liferay.portal.NoSuchUserGroupException if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup findByPrimaryKey(long userGroupId)
		throws com.liferay.portal.NoSuchUserGroupException;

	/**
	* Returns the user group with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param userGroupId the primary key of the user group
	* @return the user group, or <code>null</code> if a user group with the primary key could not be found
	*/
	public com.liferay.portal.model.UserGroup fetchByPrimaryKey(
		long userGroupId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.model.UserGroup> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the user groups.
	*
	* @return the user groups
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findAll();

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
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findAll(
		int start, int end);

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
	*/
	public java.util.List<com.liferay.portal.model.UserGroup> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Removes all the user groups from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of user groups.
	*
	* @return the number of user groups
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of groups associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return long[] of the primaryKeys of groups associated with the user group
	*/
	public long[] getGroupPrimaryKeys(long pk);

	/**
	* Returns all the groups associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the groups associated with the user group
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk);

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
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end);

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
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the number of groups associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the number of groups associated with the user group
	*/
	public int getGroupsSize(long pk);

	/**
	* Returns <code>true</code> if the group is associated with the user group.
	*
	* @param pk the primary key of the user group
	* @param groupPK the primary key of the group
	* @return <code>true</code> if the group is associated with the user group; <code>false</code> otherwise
	*/
	public boolean containsGroup(long pk, long groupPK);

	/**
	* Returns <code>true</code> if the user group has any groups associated with it.
	*
	* @param pk the primary key of the user group to check for associations with groups
	* @return <code>true</code> if the user group has any groups associated with it; <code>false</code> otherwise
	*/
	public boolean containsGroups(long pk);

	/**
	* Adds an association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPK the primary key of the group
	*/
	public void addGroup(long pk, long groupPK);

	/**
	* Adds an association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param group the group
	*/
	public void addGroup(long pk, com.liferay.portal.model.Group group);

	/**
	* Adds an association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPKs the primary keys of the groups
	*/
	public void addGroups(long pk, long[] groupPKs);

	/**
	* Adds an association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groups the groups
	*/
	public void addGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups);

	/**
	* Clears all associations between the user group and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to clear the associated groups from
	*/
	public void clearGroups(long pk);

	/**
	* Removes the association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPK the primary key of the group
	*/
	public void removeGroup(long pk, long groupPK);

	/**
	* Removes the association between the user group and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param group the group
	*/
	public void removeGroup(long pk, com.liferay.portal.model.Group group);

	/**
	* Removes the association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPKs the primary keys of the groups
	*/
	public void removeGroups(long pk, long[] groupPKs);

	/**
	* Removes the association between the user group and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groups the groups
	*/
	public void removeGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups);

	/**
	* Sets the groups associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groupPKs the primary keys of the groups to be associated with the user group
	*/
	public void setGroups(long pk, long[] groupPKs);

	/**
	* Sets the groups associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param groups the groups to be associated with the user group
	*/
	public void setGroups(long pk,
		java.util.List<com.liferay.portal.model.Group> groups);

	/**
	* Returns the primaryKeys of teams associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return long[] of the primaryKeys of teams associated with the user group
	*/
	public long[] getTeamPrimaryKeys(long pk);

	/**
	* Returns all the teams associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the teams associated with the user group
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk);

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
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk,
		int start, int end);

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
	*/
	public java.util.List<com.liferay.portal.model.Team> getTeams(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the number of teams associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the number of teams associated with the user group
	*/
	public int getTeamsSize(long pk);

	/**
	* Returns <code>true</code> if the team is associated with the user group.
	*
	* @param pk the primary key of the user group
	* @param teamPK the primary key of the team
	* @return <code>true</code> if the team is associated with the user group; <code>false</code> otherwise
	*/
	public boolean containsTeam(long pk, long teamPK);

	/**
	* Returns <code>true</code> if the user group has any teams associated with it.
	*
	* @param pk the primary key of the user group to check for associations with teams
	* @return <code>true</code> if the user group has any teams associated with it; <code>false</code> otherwise
	*/
	public boolean containsTeams(long pk);

	/**
	* Adds an association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPK the primary key of the team
	*/
	public void addTeam(long pk, long teamPK);

	/**
	* Adds an association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param team the team
	*/
	public void addTeam(long pk, com.liferay.portal.model.Team team);

	/**
	* Adds an association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPKs the primary keys of the teams
	*/
	public void addTeams(long pk, long[] teamPKs);

	/**
	* Adds an association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teams the teams
	*/
	public void addTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams);

	/**
	* Clears all associations between the user group and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to clear the associated teams from
	*/
	public void clearTeams(long pk);

	/**
	* Removes the association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPK the primary key of the team
	*/
	public void removeTeam(long pk, long teamPK);

	/**
	* Removes the association between the user group and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param team the team
	*/
	public void removeTeam(long pk, com.liferay.portal.model.Team team);

	/**
	* Removes the association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPKs the primary keys of the teams
	*/
	public void removeTeams(long pk, long[] teamPKs);

	/**
	* Removes the association between the user group and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teams the teams
	*/
	public void removeTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams);

	/**
	* Sets the teams associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teamPKs the primary keys of the teams to be associated with the user group
	*/
	public void setTeams(long pk, long[] teamPKs);

	/**
	* Sets the teams associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param teams the teams to be associated with the user group
	*/
	public void setTeams(long pk,
		java.util.List<com.liferay.portal.model.Team> teams);

	/**
	* Returns the primaryKeys of users associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return long[] of the primaryKeys of users associated with the user group
	*/
	public long[] getUserPrimaryKeys(long pk);

	/**
	* Returns all the users associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the users associated with the user group
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk);

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
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end);

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
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers(long pk,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator);

	/**
	* Returns the number of users associated with the user group.
	*
	* @param pk the primary key of the user group
	* @return the number of users associated with the user group
	*/
	public int getUsersSize(long pk);

	/**
	* Returns <code>true</code> if the user is associated with the user group.
	*
	* @param pk the primary key of the user group
	* @param userPK the primary key of the user
	* @return <code>true</code> if the user is associated with the user group; <code>false</code> otherwise
	*/
	public boolean containsUser(long pk, long userPK);

	/**
	* Returns <code>true</code> if the user group has any users associated with it.
	*
	* @param pk the primary key of the user group to check for associations with users
	* @return <code>true</code> if the user group has any users associated with it; <code>false</code> otherwise
	*/
	public boolean containsUsers(long pk);

	/**
	* Adds an association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPK the primary key of the user
	*/
	public void addUser(long pk, long userPK);

	/**
	* Adds an association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param user the user
	*/
	public void addUser(long pk, com.liferay.portal.model.User user);

	/**
	* Adds an association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPKs the primary keys of the users
	*/
	public void addUsers(long pk, long[] userPKs);

	/**
	* Adds an association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param users the users
	*/
	public void addUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users);

	/**
	* Clears all associations between the user group and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group to clear the associated users from
	*/
	public void clearUsers(long pk);

	/**
	* Removes the association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPK the primary key of the user
	*/
	public void removeUser(long pk, long userPK);

	/**
	* Removes the association between the user group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param user the user
	*/
	public void removeUser(long pk, com.liferay.portal.model.User user);

	/**
	* Removes the association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPKs the primary keys of the users
	*/
	public void removeUsers(long pk, long[] userPKs);

	/**
	* Removes the association between the user group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param users the users
	*/
	public void removeUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users);

	/**
	* Sets the users associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param userPKs the primary keys of the users to be associated with the user group
	*/
	public void setUsers(long pk, long[] userPKs);

	/**
	* Sets the users associated with the user group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the user group
	* @param users the users to be associated with the user group
	*/
	public void setUsers(long pk,
		java.util.List<com.liferay.portal.model.User> users);
}
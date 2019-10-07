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

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for UserGroup. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface UserGroupService extends BaseService {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link UserGroupServiceUtil} to access the user group remote service. Add custom service methods to <code>com.liferay.portal.service.impl.UserGroupServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	 * Adds the user groups to the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userGroupIds the primary keys of the user groups
	 */
	public void addGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException;

	/**
	 * Adds the user groups to the team
	 *
	 * @param teamId the primary key of the team
	 * @param userGroupIds the primary keys of the user groups
	 */
	public void addTeamUserGroups(long teamId, long[] userGroupIds)
		throws PortalException;

	/**
	 * Adds a user group.
	 *
	 * <p>
	 * This method handles the creation and bookkeeping of the user group,
	 * including its resources, metadata, and internal data structures.
	 * </p>
	 *
	 * @param name the user group's name
	 * @param description the user group's description
	 * @param serviceContext the service context to be applied (optionally
	 <code>null</code>). Can set expando bridge attributes for the
	 user group.
	 * @return the user group
	 */
	public UserGroup addUserGroup(
			String name, String description, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * Deletes the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 */
	public void deleteUserGroup(long userGroupId) throws PortalException;

	/**
	 * Fetches the user group with the primary key.
	 *
	 * @param userGroupId the primary key of the user group
	 * @return the user group with the primary key
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserGroup fetchUserGroup(long userGroupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroup> getGtUserGroups(
		long gtUserGroupId, long companyId, long parentUserGroupId, int size);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * Returns the user group with the primary key.
	 *
	 * @param userGroupId the primary key of the user group
	 * @return the user group with the primary key
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserGroup getUserGroup(long userGroupId) throws PortalException;

	/**
	 * Returns the user group with the name.
	 *
	 * @param name the user group's name
	 * @return the user group with the name
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public UserGroup getUserGroup(String name) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroup> getUserGroups(long companyId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroup> getUserGroups(
		long companyId, String name, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getUserGroupsCount(long companyId, String name);

	/**
	 * Returns all the user groups to which the user belongs.
	 *
	 * @param userId the primary key of the user
	 * @return the user groups to which the user belongs
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroup> getUserUserGroups(long userId)
		throws PortalException;

	/**
	 * Returns an ordered range of all the user groups that match the keywords.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the user group's company
	 * @param keywords the keywords (space separated), which may occur in the
	 user group's name or description (optionally <code>null</code>)
	 * @param params the finder params (optionally <code>null</code>). For more
	 information see {@link
	 com.liferay.portal.kernel.service.persistence.UserGroupFinder}
	 * @param start the lower bound of the range of user groups to return
	 * @param end the upper bound of the range of user groups to return (not
	 inclusive)
	 * @param obc the comparator to order the user groups (optionally
	 <code>null</code>)
	 * @return the matching user groups ordered by comparator <code>obc</code>
	 * @see com.liferay.portal.kernel.service.persistence.UserGroupFinder
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroup> search(
		long companyId, String keywords, LinkedHashMap<String, Object> params,
		int start, int end, OrderByComparator<UserGroup> obc);

	/**
	 * Returns an ordered range of all the user groups that match the name and
	 * description.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param companyId the primary key of the user group's company
	 * @param name the user group's name (optionally <code>null</code>)
	 * @param description the user group's description (optionally
	 <code>null</code>)
	 * @param params the finder params (optionally <code>null</code>). For more
	 information see {@link
	 com.liferay.portal.kernel.service.persistence.UserGroupFinder}
	 * @param andOperator whether every field must match its keywords or just
	 one field
	 * @param start the lower bound of the range of user groups to return
	 * @param end the upper bound of the range of user groups to return (not
	 inclusive)
	 * @param obc the comparator to order the user groups (optionally
	 <code>null</code>)
	 * @return the matching user groups ordered by comparator <code>obc</code>
	 * @see com.liferay.portal.kernel.service.persistence.UserGroupFinder
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<UserGroup> search(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator, int start,
		int end, OrderByComparator<UserGroup> obc);

	/**
	 * Returns the number of user groups that match the keywords
	 *
	 * @param companyId the primary key of the user group's company
	 * @param keywords the keywords (space separated), which may occur in the
	 user group's name or description (optionally <code>null</code>)
	 * @param params the finder params (optionally <code>null</code>). For more
	 information see {@link
	 com.liferay.portal.kernel.service.persistence.UserGroupFinder}
	 * @return the number of matching user groups
	 * @see com.liferay.portal.kernel.service.persistence.UserGroupFinder
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(
		long companyId, String keywords, LinkedHashMap<String, Object> params);

	/**
	 * Returns the number of user groups that match the name and description.
	 *
	 * @param companyId the primary key of the user group's company
	 * @param name the user group's name (optionally <code>null</code>)
	 * @param description the user group's description (optionally
	 <code>null</code>)
	 * @param params the finder params (optionally <code>null</code>). For more
	 information see {@link
	 com.liferay.portal.kernel.service.persistence.UserGroupFinder}
	 * @param andOperator whether every field must match its keywords or just
	 one field
	 * @return the number of matching user groups
	 * @see com.liferay.portal.kernel.service.persistence.UserGroupFinder
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(
		long companyId, String name, String description,
		LinkedHashMap<String, Object> params, boolean andOperator);

	/**
	 * Removes the user groups from the group.
	 *
	 * @param groupId the primary key of the group
	 * @param userGroupIds the primary keys of the user groups
	 */
	public void unsetGroupUserGroups(long groupId, long[] userGroupIds)
		throws PortalException;

	/**
	 * Removes the user groups from the team.
	 *
	 * @param teamId the primary key of the team
	 * @param userGroupIds the primary keys of the user groups
	 */
	public void unsetTeamUserGroups(long teamId, long[] userGroupIds)
		throws PortalException;

	/**
	 * Updates the user group.
	 *
	 * @param userGroupId the primary key of the user group
	 * @param name the user group's name
	 * @param description the the user group's description
	 * @param serviceContext the service context to be applied (optionally
	 <code>null</code>). Can set expando bridge attributes for the
	 user group.
	 * @return the user group
	 */
	public UserGroup updateUserGroup(
			long userGroupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException;

}
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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for UserGroup. This utility wraps
 * {@link com.liferay.portal.service.impl.UserGroupLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupLocalService
 * @see com.liferay.portal.service.base.UserGroupLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.UserGroupLocalServiceImpl
 * @generated
 */
@ProviderType
public class UserGroupLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.UserGroupLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addGroupUserGroup(long groupId,
		com.liferay.portal.model.UserGroup userGroup) {
		getService().addGroupUserGroup(groupId, userGroup);
	}

	public static void addGroupUserGroup(long groupId, long userGroupId) {
		getService().addGroupUserGroup(groupId, userGroupId);
	}

	public static void addGroupUserGroups(long groupId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups) {
		getService().addGroupUserGroups(groupId, UserGroups);
	}

	public static void addGroupUserGroups(long groupId, long[] userGroupIds) {
		getService().addGroupUserGroups(groupId, userGroupIds);
	}

	public static void addTeamUserGroup(long teamId,
		com.liferay.portal.model.UserGroup userGroup) {
		getService().addTeamUserGroup(teamId, userGroup);
	}

	public static void addTeamUserGroup(long teamId, long userGroupId) {
		getService().addTeamUserGroup(teamId, userGroupId);
	}

	public static void addTeamUserGroups(long teamId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups) {
		getService().addTeamUserGroups(teamId, UserGroups);
	}

	public static void addTeamUserGroups(long teamId, long[] userGroupIds) {
		getService().addTeamUserGroups(teamId, userGroupIds);
	}

	/**
	* Adds the user group to the database. Also notifies the appropriate model listeners.
	*
	* @param userGroup the user group
	* @return the user group that was added
	*/
	public static com.liferay.portal.model.UserGroup addUserGroup(
		com.liferay.portal.model.UserGroup userGroup) {
		return getService().addUserGroup(userGroup);
	}

	/**
	* Adds a user group.
	*
	* <p>
	* This method handles the creation and bookkeeping of the user group,
	* including its resources, metadata, and internal data structures. It is
	* not necessary to make subsequent calls to setup default groups and
	* resources for the user group.
	* </p>
	*
	* @param userId the primary key of the user
	* @param companyId the primary key of the user group's company
	* @param name the user group's name
	* @param description the user group's description
	* @return the user group
	* @deprecated As of 6.2.0, replaced by {@link #addUserGroup(long, long,
	String, String, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.UserGroup addUserGroup(long userId,
		long companyId, java.lang.String name, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addUserGroup(userId, companyId, name, description);
	}

	/**
	* Adds a user group.
	*
	* <p>
	* This method handles the creation and bookkeeping of the user group,
	* including its resources, metadata, and internal data structures. It is
	* not necessary to make subsequent calls to setup default groups and
	* resources for the user group.
	* </p>
	*
	* @param userId the primary key of the user
	* @param companyId the primary key of the user group's company
	* @param name the user group's name
	* @param description the user group's description
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user group.
	* @return the user group
	*/
	public static com.liferay.portal.model.UserGroup addUserGroup(long userId,
		long companyId, java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addUserGroup(userId, companyId, name, description,
			serviceContext);
	}

	public static void addUserUserGroup(long userId,
		com.liferay.portal.model.UserGroup userGroup) {
		getService().addUserUserGroup(userId, userGroup);
	}

	public static void addUserUserGroup(long userId, long userGroupId) {
		getService().addUserUserGroup(userId, userGroupId);
	}

	public static void addUserUserGroups(long userId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups) {
		getService().addUserUserGroups(userId, UserGroups);
	}

	public static void addUserUserGroups(long userId, long[] userGroupIds) {
		getService().addUserUserGroups(userId, userGroupIds);
	}

	public static void clearGroupUserGroups(long groupId) {
		getService().clearGroupUserGroups(groupId);
	}

	public static void clearTeamUserGroups(long teamId) {
		getService().clearTeamUserGroups(teamId);
	}

	public static void clearUserUserGroups(long userId) {
		getService().clearUserUserGroups(userId);
	}

	/**
	* Copies the user group's layout to the user.
	*
	* @param userGroupId the primary key of the user group
	* @param userId the primary key of the user
	* @deprecated As of 6.2.0
	*/
	@Deprecated
	public static void copyUserGroupLayouts(long userGroupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().copyUserGroupLayouts(userGroupId, userId);
	}

	/**
	* Copies the user group's layouts to the users who are not already members
	* of the user group.
	*
	* @param userGroupId the primary key of the user group
	* @param userIds the primary keys of the users
	* @deprecated As of 6.1.0
	*/
	@Deprecated
	public static void copyUserGroupLayouts(long userGroupId, long[] userIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().copyUserGroupLayouts(userGroupId, userIds);
	}

	/**
	* Copies the user groups' layouts to the user.
	*
	* @param userGroupIds the primary keys of the user groups
	* @param userId the primary key of the user
	* @deprecated As of 6.1.0
	*/
	@Deprecated
	public static void copyUserGroupLayouts(long[] userGroupIds, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().copyUserGroupLayouts(userGroupIds, userId);
	}

	/**
	* Creates a new user group with the primary key. Does not add the user group to the database.
	*
	* @param userGroupId the primary key for the new user group
	* @return the new user group
	*/
	public static com.liferay.portal.model.UserGroup createUserGroup(
		long userGroupId) {
		return getService().createUserGroup(userGroupId);
	}

	public static void deleteGroupUserGroup(long groupId,
		com.liferay.portal.model.UserGroup userGroup) {
		getService().deleteGroupUserGroup(groupId, userGroup);
	}

	public static void deleteGroupUserGroup(long groupId, long userGroupId) {
		getService().deleteGroupUserGroup(groupId, userGroupId);
	}

	public static void deleteGroupUserGroups(long groupId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups) {
		getService().deleteGroupUserGroups(groupId, UserGroups);
	}

	public static void deleteGroupUserGroups(long groupId, long[] userGroupIds) {
		getService().deleteGroupUserGroups(groupId, userGroupIds);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static void deleteTeamUserGroup(long teamId,
		com.liferay.portal.model.UserGroup userGroup) {
		getService().deleteTeamUserGroup(teamId, userGroup);
	}

	public static void deleteTeamUserGroup(long teamId, long userGroupId) {
		getService().deleteTeamUserGroup(teamId, userGroupId);
	}

	public static void deleteTeamUserGroups(long teamId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups) {
		getService().deleteTeamUserGroups(teamId, UserGroups);
	}

	public static void deleteTeamUserGroups(long teamId, long[] userGroupIds) {
		getService().deleteTeamUserGroups(teamId, userGroupIds);
	}

	/**
	* Deletes the user group from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroup the user group
	* @return the user group that was removed
	* @throws PortalException
	*/
	public static com.liferay.portal.model.UserGroup deleteUserGroup(
		com.liferay.portal.model.UserGroup userGroup)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteUserGroup(userGroup);
	}

	/**
	* Deletes the user group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param userGroupId the primary key of the user group
	* @return the user group that was removed
	* @throws PortalException if a user group with the primary key could not be found
	*/
	public static com.liferay.portal.model.UserGroup deleteUserGroup(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteUserGroup(userGroupId);
	}

	public static void deleteUserGroups(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteUserGroups(companyId);
	}

	public static void deleteUserUserGroup(long userId,
		com.liferay.portal.model.UserGroup userGroup) {
		getService().deleteUserUserGroup(userId, userGroup);
	}

	public static void deleteUserUserGroup(long userId, long userGroupId) {
		getService().deleteUserUserGroup(userId, userGroupId);
	}

	public static void deleteUserUserGroups(long userId,
		java.util.List<com.liferay.portal.model.UserGroup> UserGroups) {
		getService().deleteUserUserGroups(userId, UserGroups);
	}

	public static void deleteUserUserGroups(long userId, long[] userGroupIds) {
		getService().deleteUserUserGroups(userId, userGroupIds);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserGroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.model.UserGroup fetchUserGroup(
		long companyId, java.lang.String name) {
		return getService().fetchUserGroup(companyId, name);
	}

	public static com.liferay.portal.model.UserGroup fetchUserGroup(
		long userGroupId) {
		return getService().fetchUserGroup(userGroupId);
	}

	/**
	* Returns the user group with the matching UUID and company.
	*
	* @param uuid the user group's UUID
	* @param companyId the primary key of the company
	* @return the matching user group, or <code>null</code> if a matching user group could not be found
	*/
	public static com.liferay.portal.model.UserGroup fetchUserGroupByUuidAndCompanyId(
		java.lang.String uuid, long companyId) {
		return getService().fetchUserGroupByUuidAndCompanyId(uuid, companyId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery getExportActionableDynamicQuery(
		com.liferay.portlet.exportimport.lar.PortletDataContext portletDataContext) {
		return getService().getExportActionableDynamicQuery(portletDataContext);
	}

	/**
	* Returns the groupIds of the groups associated with the user group.
	*
	* @param userGroupId the userGroupId of the user group
	* @return long[] the groupIds of groups associated with the user group
	*/
	public static long[] getGroupPrimaryKeys(long userGroupId) {
		return getService().getGroupPrimaryKeys(userGroupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getGroupUserGroups(
		long groupId) {
		return getService().getGroupUserGroups(groupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getGroupUserGroups(
		long groupId, int start, int end) {
		return getService().getGroupUserGroups(groupId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getGroupUserGroups(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.UserGroup> orderByComparator) {
		return getService()
				   .getGroupUserGroups(groupId, start, end, orderByComparator);
	}

	public static int getGroupUserGroupsCount(long groupId) {
		return getService().getGroupUserGroupsCount(groupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getGroupUserUserGroups(
		long groupId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getGroupUserUserGroups(groupId, userId);
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the teamIds of the teams associated with the user group.
	*
	* @param userGroupId the userGroupId of the user group
	* @return long[] the teamIds of teams associated with the user group
	*/
	public static long[] getTeamPrimaryKeys(long userGroupId) {
		return getService().getTeamPrimaryKeys(userGroupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getTeamUserGroups(
		long teamId) {
		return getService().getTeamUserGroups(teamId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getTeamUserGroups(
		long teamId, int start, int end) {
		return getService().getTeamUserGroups(teamId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getTeamUserGroups(
		long teamId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.UserGroup> orderByComparator) {
		return getService()
				   .getTeamUserGroups(teamId, start, end, orderByComparator);
	}

	public static int getTeamUserGroupsCount(long teamId) {
		return getService().getTeamUserGroupsCount(teamId);
	}

	/**
	* Returns the user group with the name.
	*
	* @param companyId the primary key of the user group's company
	* @param name the user group's name
	* @return Returns the user group with the name
	*/
	public static com.liferay.portal.model.UserGroup getUserGroup(
		long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserGroup(companyId, name);
	}

	/**
	* Returns the user group with the primary key.
	*
	* @param userGroupId the primary key of the user group
	* @return the user group
	* @throws PortalException if a user group with the primary key could not be found
	*/
	public static com.liferay.portal.model.UserGroup getUserGroup(
		long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserGroup(userGroupId);
	}

	/**
	* Returns the user group with the matching UUID and company.
	*
	* @param uuid the user group's UUID
	* @param companyId the primary key of the company
	* @return the matching user group
	* @throws PortalException if a matching user group could not be found
	*/
	public static com.liferay.portal.model.UserGroup getUserGroupByUuidAndCompanyId(
		java.lang.String uuid, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserGroupByUuidAndCompanyId(uuid, companyId);
	}

	/**
	* Returns all the user groups belonging to the company.
	*
	* @param companyId the primary key of the user groups' company
	* @return the user groups belonging to the company
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long companyId) {
		return getService().getUserGroups(companyId);
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
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		int start, int end) {
		return getService().getUserGroups(start, end);
	}

	/**
	* Returns all the user groups with the primary keys.
	*
	* @param userGroupIds the primary keys of the user groups
	* @return the user groups with the primary keys
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> getUserGroups(
		long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getUserGroups(userGroupIds);
	}

	/**
	* Returns the number of user groups.
	*
	* @return the number of user groups
	*/
	public static int getUserGroupsCount() {
		return getService().getUserGroupsCount();
	}

	/**
	* Returns the userIds of the users associated with the user group.
	*
	* @param userGroupId the userGroupId of the user group
	* @return long[] the userIds of users associated with the user group
	*/
	public static long[] getUserPrimaryKeys(long userGroupId) {
		return getService().getUserPrimaryKeys(userGroupId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getUserUserGroups(
		long userId) {
		return getService().getUserUserGroups(userId);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getUserUserGroups(
		long userId, int start, int end) {
		return getService().getUserUserGroups(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.UserGroup> getUserUserGroups(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.UserGroup> orderByComparator) {
		return getService()
				   .getUserUserGroups(userId, start, end, orderByComparator);
	}

	public static int getUserUserGroupsCount(long userId) {
		return getService().getUserUserGroupsCount(userId);
	}

	public static boolean hasGroupUserGroup(long groupId, long userGroupId) {
		return getService().hasGroupUserGroup(groupId, userGroupId);
	}

	public static boolean hasGroupUserGroups(long groupId) {
		return getService().hasGroupUserGroups(groupId);
	}

	public static boolean hasTeamUserGroup(long teamId, long userGroupId) {
		return getService().hasTeamUserGroup(teamId, userGroupId);
	}

	public static boolean hasTeamUserGroups(long teamId) {
		return getService().hasTeamUserGroups(teamId);
	}

	public static boolean hasUserUserGroup(long userId, long userGroupId) {
		return getService().hasUserUserGroup(userId, userGroupId);
	}

	public static boolean hasUserUserGroups(long userId) {
		return getService().hasUserUserGroups(userId);
	}

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
	com.liferay.portal.service.persistence.UserGroupFinder}
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not
	inclusive)
	* @param obc the comparator to order the user groups (optionally
	<code>null</code>)
	* @return the matching user groups ordered by comparator <code>obc</code>
	* @see com.liferay.portal.service.persistence.UserGroupFinder
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> search(
		long companyId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.UserGroup> obc) {
		return getService().search(companyId, keywords, params, start, end, obc);
	}

	/**
	* Returns an ordered range of all the user groups that match the keywords,
	* using the indexer. It is preferable to use this method instead of the
	* non-indexed version whenever possible for performance reasons.
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
	com.liferay.portlet.usergroupsadmin.util.UserGroupIndexer}
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not
	inclusive)
	* @param sort the field and direction by which to sort (optionally
	<code>null</code>)
	* @return the matching user groups ordered by sort
	* @see com.liferay.portlet.usergroupsadmin.util.UserGroupIndexer
	*/
	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort) {
		return getService().search(companyId, keywords, params, start, end, sort);
	}

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
	com.liferay.portal.service.persistence.UserGroupFinder}
	* @param andOperator whether every field must match its keywords or just
	one field
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not
	inclusive)
	* @param obc the comparator to order the user groups (optionally
	<code>null</code>)
	* @return the matching user groups ordered by comparator <code>obc</code>
	* @see com.liferay.portal.service.persistence.UserGroupFinder
	*/
	public static java.util.List<com.liferay.portal.model.UserGroup> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.model.UserGroup> obc) {
		return getService()
				   .search(companyId, name, description, params, andOperator,
			start, end, obc);
	}

	/**
	* Returns an ordered range of all the user groups that match the name and
	* description. It is preferable to use this method instead of the
	* non-indexed version whenever possible for performance reasons.
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
	com.liferay.portlet.usergroupsadmin.util.UserGroupIndexer}
	* @param andSearch whether every field must match its keywords or just one
	field
	* @param start the lower bound of the range of user groups to return
	* @param end the upper bound of the range of user groups to return (not
	inclusive)
	* @param sort the field and direction by which to sort (optionally
	<code>null</code>)
	* @return the matching user groups ordered by sort
	* @see com.liferay.portal.service.persistence.UserGroupFinder
	*/
	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort) {
		return getService()
				   .search(companyId, name, description, params, andSearch,
			start, end, sort);
	}

	/**
	* Returns the number of user groups that match the keywords
	*
	* @param companyId the primary key of the user group's company
	* @param keywords the keywords (space separated), which may occur in the
	user group's name or description (optionally <code>null</code>)
	* @param params the finder params (optionally <code>null</code>). For more
	information see {@link
	com.liferay.portal.service.persistence.UserGroupFinder}
	* @return the number of matching user groups
	* @see com.liferay.portal.service.persistence.UserGroupFinder
	*/
	public static int searchCount(long companyId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params) {
		return getService().searchCount(companyId, keywords, params);
	}

	/**
	* Returns the number of user groups that match the name and description.
	*
	* @param companyId the primary key of the user group's company
	* @param name the user group's name (optionally <code>null</code>)
	* @param description the user group's description (optionally
	<code>null</code>)
	* @param params the finder params (optionally <code>null</code>). For more
	information see {@link
	com.liferay.portal.service.persistence.UserGroupFinder}
	* @param andOperator whether every field must match its keywords or just
	one field
	* @return the number of matching user groups
	* @see com.liferay.portal.service.persistence.UserGroupFinder
	*/
	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andOperator) {
		return getService()
				   .searchCount(companyId, name, description, params,
			andOperator);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.model.UserGroup> searchUserGroups(
		long companyId, java.lang.String keywords,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchUserGroups(companyId, keywords, params, start, end,
			sort);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.portal.model.UserGroup> searchUserGroups(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		boolean andSearch, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchUserGroups(companyId, name, description, params,
			andSearch, start, end, sort);
	}

	public static void setGroupUserGroups(long groupId, long[] userGroupIds) {
		getService().setGroupUserGroups(groupId, userGroupIds);
	}

	public static void setTeamUserGroups(long teamId, long[] userGroupIds) {
		getService().setTeamUserGroups(teamId, userGroupIds);
	}

	/**
	* @throws PortalException
	*/
	public static void setUserUserGroups(long userId, long[] userGroupIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().setUserUserGroups(userId, userGroupIds);
	}

	/**
	* Removes the user groups from the group.
	*
	* @param groupId the primary key of the group
	* @param userGroupIds the primary keys of the user groups
	*/
	public static void unsetGroupUserGroups(long groupId, long[] userGroupIds) {
		getService().unsetGroupUserGroups(groupId, userGroupIds);
	}

	/**
	* Removes the user groups from the team.
	*
	* @param teamId the primary key of the team
	* @param userGroupIds the primary keys of the user groups
	*/
	public static void unsetTeamUserGroups(long teamId, long[] userGroupIds) {
		getService().unsetTeamUserGroups(teamId, userGroupIds);
	}

	/**
	* Updates the user group.
	*
	* @param companyId the primary key of the user group's company
	* @param userGroupId the primary key of the user group
	* @param name the user group's name
	* @param description the user group's description
	* @return the user group
	* @deprecated As of 6.2.0, replaced by {@link #updateUserGroup(long, long,
	String, String, ServiceContext)}
	*/
	@Deprecated
	public static com.liferay.portal.model.UserGroup updateUserGroup(
		long companyId, long userGroupId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateUserGroup(companyId, userGroupId, name, description);
	}

	/**
	* Updates the user group.
	*
	* @param companyId the primary key of the user group's company
	* @param userGroupId the primary key of the user group
	* @param name the user group's name
	* @param description the user group's description
	* @param serviceContext the service context to be applied (optionally
	<code>null</code>). Can set expando bridge attributes for the
	user group.
	* @return the user group
	*/
	public static com.liferay.portal.model.UserGroup updateUserGroup(
		long companyId, long userGroupId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateUserGroup(companyId, userGroupId, name, description,
			serviceContext);
	}

	/**
	* Updates the user group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param userGroup the user group
	* @return the user group that was updated
	*/
	public static com.liferay.portal.model.UserGroup updateUserGroup(
		com.liferay.portal.model.UserGroup userGroup) {
		return getService().updateUserGroup(userGroup);
	}

	public static UserGroupLocalService getService() {
		if (_service == null) {
			_service = (UserGroupLocalService)PortalBeanLocatorUtil.locate(UserGroupLocalService.class.getName());

			ReferenceRegistry.registerReference(UserGroupLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static UserGroupLocalService _service;
}
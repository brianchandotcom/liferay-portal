/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link GroupLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       GroupLocalService
 * @generated
 */
public class GroupLocalServiceWrapper implements GroupLocalService {
	public GroupLocalServiceWrapper(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	/**
	* Adds the group to the database. Also notifies the appropriate model listeners.
	*
	* @param group the group
	* @return the group that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Group addGroup(
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.addGroup(group);
	}

	/**
	* Creates a new group with the primary key. Does not add the group to the database.
	*
	* @param groupId the primary key for the new group
	* @return the new group
	*/
	public com.liferay.portal.model.Group createGroup(long groupId) {
		return _groupLocalService.createGroup(groupId);
	}

	/**
	* Deletes the group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param groupId the primary key of the group
	* @throws PortalException if a group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.deleteGroup(groupId);
	}

	/**
	* Deletes the group from the database. Also notifies the appropriate model listeners.
	*
	* @param group the group
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteGroup(com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.deleteGroup(group);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the group with the primary key.
	*
	* @param groupId the primary key of the group
	* @return the group
	* @throws PortalException if a group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Group getGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getGroup(groupId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the groups.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of groups
	* @param end the upper bound of the range of groups (not inclusive)
	* @return the range of groups
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Group> getGroups(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getGroups(start, end);
	}

	/**
	* Returns the number of groups.
	*
	* @return the number of groups
	* @throws SystemException if a system exception occurred
	*/
	public int getGroupsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getGroupsCount();
	}

	/**
	* Updates the group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param group the group
	* @return the group that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Group updateGroup(
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateGroup(group);
	}

	/**
	* Updates the group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param group the group
	* @param merge whether to merge the group with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the group that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Group updateGroup(
		com.liferay.portal.model.Group group, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateGroup(group, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _groupLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_groupLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Group addGroup(long userId,
		java.lang.String className, long classPK, long liveGroupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.addGroup(userId, className, classPK,
			liveGroupId, name, description, type, friendlyURL, site, active,
			serviceContext);
	}

	public com.liferay.portal.model.Group addGroup(long userId,
		java.lang.String className, long classPK, java.lang.String name,
		java.lang.String description, int type, java.lang.String friendlyURL,
		boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.addGroup(userId, className, classPK, name,
			description, type, friendlyURL, site, active, serviceContext);
	}

	public void addRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.addRoleGroups(roleId, groupIds);
	}

	public void addUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.addUserGroups(userId, groupIds);
	}

	public void checkCompanyGroup(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.checkCompanyGroup(companyId);
	}

	public void checkSystemGroups(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.checkSystemGroups(companyId);
	}

	public com.liferay.portal.model.Group fetchGroup(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.fetchGroup(groupId);
	}

	public com.liferay.portal.model.Group fetchGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.fetchGroup(companyId, name);
	}

	public com.liferay.portal.model.Group getCompanyGroup(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getCompanyGroup(companyId);
	}

	public java.util.List<com.liferay.portal.model.Group> getCompanyGroups(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getCompanyGroups(companyId, start, end);
	}

	public int getCompanyGroupsCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getCompanyGroupsCount(companyId);
	}

	public com.liferay.portal.model.Group getFriendlyURLGroup(long companyId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getFriendlyURLGroup(companyId, friendlyURL);
	}

	public com.liferay.portal.model.Group getGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getGroup(companyId, name);
	}

	public java.util.List<com.liferay.portal.model.Group> getGroups(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getGroups(groupIds);
	}

	public com.liferay.portal.model.Group getLayoutGroup(long companyId,
		long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getLayoutGroup(companyId, plid);
	}

	public com.liferay.portal.model.Group getLayoutPrototypeGroup(
		long companyId, long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getLayoutPrototypeGroup(companyId,
			layoutPrototypeId);
	}

	public com.liferay.portal.model.Group getLayoutSetPrototypeGroup(
		long companyId, long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getLayoutSetPrototypeGroup(companyId,
			layoutSetPrototypeId);
	}

	public java.util.List<com.liferay.portal.model.Group> getLiveGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getLiveGroups();
	}

	public java.util.List<com.liferay.portal.model.Group> getNoLayoutsGroups(
		java.lang.String className, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getNoLayoutsGroups(className, privateLayout,
			start, end);
	}

	public java.util.List<com.liferay.portal.model.Group> getNullFriendlyURLGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getNullFriendlyURLGroups();
	}

	public com.liferay.portal.model.Group getOrganizationGroup(long companyId,
		long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getOrganizationGroup(companyId, organizationId);
	}

	public java.util.List<com.liferay.portal.model.Group> getOrganizationsGroups(
		java.util.List<com.liferay.portal.model.Organization> organizations) {
		return _groupLocalService.getOrganizationsGroups(organizations);
	}

	public java.util.List<com.liferay.portal.model.Group> getOrganizationsRelatedGroups(
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getOrganizationsRelatedGroups(organizations);
	}

	public java.util.List<com.liferay.portal.model.Group> getRoleGroups(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getRoleGroups(roleId);
	}

	public com.liferay.portal.model.Group getStagingGroup(long liveGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getStagingGroup(liveGroupId);
	}

	public com.liferay.portal.model.Group getUserGroup(long companyId,
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroup(companyId, userId);
	}

	public com.liferay.portal.model.Group getUserGroupGroup(long companyId,
		long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroupGroup(companyId, userGroupId);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroups(userId);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId, boolean inherit)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroups(userId, inherit);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId, boolean inherit, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroups(userId, inherit, start, end);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroups(userId, start, end);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroupsGroups(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroupsGroups(userGroups);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserGroupsRelatedGroups(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserGroupsRelatedGroups(userGroups);
	}

	public java.util.List<com.liferay.portal.model.Group> getUserOrganizationsGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.getUserOrganizationsGroups(userId, start, end);
	}

	public boolean hasRoleGroup(long roleId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.hasRoleGroup(roleId, groupId);
	}

	public boolean hasStagingGroup(long liveGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.hasStagingGroup(liveGroupId);
	}

	public boolean hasUserGroup(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.hasUserGroup(userId, groupId);
	}

	public boolean hasUserGroup(long userId, long groupId, boolean inherit)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.hasUserGroup(userId, groupId, inherit);
	}

	public java.util.List<com.liferay.portal.model.Group> search(
		long companyId, long[] classNameIds, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.search(companyId, classNameIds, name,
			description, params, start, end);
	}

	public java.util.List<com.liferay.portal.model.Group> search(
		long companyId, long[] classNameIds, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.search(companyId, classNameIds, name,
			description, params, start, end, obc);
	}

	public java.util.List<com.liferay.portal.model.Group> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.search(companyId, name, description, params,
			start, end);
	}

	public java.util.List<com.liferay.portal.model.Group> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.search(companyId, name, description, params,
			start, end, obc);
	}

	public int searchCount(long companyId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.searchCount(companyId, name, description,
			params);
	}

	public int searchCount(long companyId, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.searchCount(companyId, classNameIds, name,
			description, params);
	}

	public void setRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.setRoleGroups(roleId, groupIds);
	}

	public void unsetRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.unsetRoleGroups(roleId, groupIds);
	}

	public void unsetUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.unsetUserGroups(userId, groupIds);
	}

	public void updateAsset(long userId, com.liferay.portal.model.Group group,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_groupLocalService.updateAsset(userId, group, assetCategoryIds,
			assetTagNames);
	}

	public com.liferay.portal.model.Group updateFriendlyURL(long groupId,
		java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateFriendlyURL(groupId, friendlyURL);
	}

	public com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateGroup(groupId, typeSettings);
	}

	public com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateGroup(groupId, name, description, type,
			friendlyURL, active, serviceContext);
	}

	public com.liferay.portal.model.Group updateSite(long groupId, boolean site)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _groupLocalService.updateSite(groupId, site);
	}

	public GroupLocalService getWrappedGroupLocalService() {
		return _groupLocalService;
	}

	public void setWrappedGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private GroupLocalService _groupLocalService;
}
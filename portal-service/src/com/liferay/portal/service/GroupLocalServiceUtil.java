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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the group local service. This utility wraps {@link com.liferay.portal.service.impl.GroupLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GroupLocalService
 * @see com.liferay.portal.service.base.GroupLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.GroupLocalServiceImpl
 * @generated
 */
public class GroupLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.GroupLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the group to the database. Also notifies the appropriate model listeners.
	*
	* @param group the group
	* @return the group that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Group addGroup(
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addGroup(group);
	}

	/**
	* Creates a new group with the primary key. Does not add the group to the database.
	*
	* @param groupId the primary key for the new group
	* @return the new group
	*/
	public static com.liferay.portal.model.Group createGroup(long groupId) {
		return getService().createGroup(groupId);
	}

	/**
	* Deletes the group with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param groupId the primary key of the group
	* @throws PortalException if a group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteGroup(groupId);
	}

	/**
	* Deletes the group from the database. Also notifies the appropriate model listeners.
	*
	* @param group the group
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteGroup(com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteGroup(group);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the group with the primary key.
	*
	* @param groupId the primary key of the group
	* @return the group
	* @throws PortalException if a group with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Group getGroup(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroup(groupId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroups(start, end);
	}

	/**
	* Returns the number of groups.
	*
	* @return the number of groups
	* @throws SystemException if a system exception occurred
	*/
	public static int getGroupsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroupsCount();
	}

	/**
	* Updates the group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param group the group
	* @return the group that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Group updateGroup(
		com.liferay.portal.model.Group group)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateGroup(group);
	}

	/**
	* Updates the group in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param group the group
	* @param merge whether to merge the group with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the group that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Group updateGroup(
		com.liferay.portal.model.Group group, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateGroup(group, merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portal.model.Group addGroup(long userId,
		java.lang.String className, long classPK, long liveGroupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addGroup(userId, className, classPK, liveGroupId, name,
			description, type, friendlyURL, site, active, serviceContext);
	}

	public static com.liferay.portal.model.Group addGroup(long userId,
		java.lang.String className, long classPK, java.lang.String name,
		java.lang.String description, int type, java.lang.String friendlyURL,
		boolean site, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addGroup(userId, className, classPK, name, description,
			type, friendlyURL, site, active, serviceContext);
	}

	public static void addRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addRoleGroups(roleId, groupIds);
	}

	public static void addUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().addUserGroups(userId, groupIds);
	}

	public static void checkCompanyGroup(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkCompanyGroup(companyId);
	}

	public static void checkSystemGroups(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkSystemGroups(companyId);
	}

	public static com.liferay.portal.model.Group fetchFriendlyURLGroup(
		long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchFriendlyURLGroup(companyId, friendlyURL);
	}

	public static com.liferay.portal.model.Group fetchGroup(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchGroup(groupId);
	}

	public static com.liferay.portal.model.Group fetchGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchGroup(companyId, name);
	}

	public static com.liferay.portal.model.Group getCompanyGroup(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyGroup(companyId);
	}

	public static java.util.List<com.liferay.portal.model.Group> getCompanyGroups(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyGroups(companyId, start, end);
	}

	public static int getCompanyGroupsCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyGroupsCount(companyId);
	}

	public static com.liferay.portal.model.Group getFriendlyURLGroup(
		long companyId, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFriendlyURLGroup(companyId, friendlyURL);
	}

	public static com.liferay.portal.model.Group getGroup(long companyId,
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroup(companyId, name);
	}

	public static java.util.List<com.liferay.portal.model.Group> getGroups(
		long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getGroups(groupIds);
	}

	public static com.liferay.portal.model.Group getLayoutGroup(
		long companyId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutGroup(companyId, plid);
	}

	public static com.liferay.portal.model.Group getLayoutPrototypeGroup(
		long companyId, long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutPrototypeGroup(companyId, layoutPrototypeId);
	}

	public static com.liferay.portal.model.Group getLayoutSetPrototypeGroup(
		long companyId, long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getLayoutSetPrototypeGroup(companyId, layoutSetPrototypeId);
	}

	public static java.util.List<com.liferay.portal.model.Group> getLiveGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLiveGroups();
	}

	public static java.util.List<com.liferay.portal.model.Group> getNoLayoutsGroups(
		java.lang.String className, boolean privateLayout, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getNoLayoutsGroups(className, privateLayout, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> getNullFriendlyURLGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getNullFriendlyURLGroups();
	}

	public static com.liferay.portal.model.Group getOrganizationGroup(
		long companyId, long organizationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationGroup(companyId, organizationId);
	}

	public static java.util.List<com.liferay.portal.model.Group> getOrganizationsGroups(
		java.util.List<com.liferay.portal.model.Organization> organizations) {
		return getService().getOrganizationsGroups(organizations);
	}

	public static java.util.List<com.liferay.portal.model.Group> getOrganizationsRelatedGroups(
		java.util.List<com.liferay.portal.model.Organization> organizations)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getOrganizationsRelatedGroups(organizations);
	}

	public static java.util.List<com.liferay.portal.model.Group> getRoleGroups(
		long roleId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRoleGroups(roleId);
	}

	public static com.liferay.portal.model.Group getStagingGroup(
		long liveGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStagingGroup(liveGroupId);
	}

	public static com.liferay.portal.model.Group getUserGroup(long companyId,
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroup(companyId, userId);
	}

	public static com.liferay.portal.model.Group getUserGroupGroup(
		long companyId, long userGroupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupGroup(companyId, userGroupId);
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroups(userId);
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId, boolean inherit)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroups(userId, inherit);
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId, boolean inherit, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroups(userId, inherit, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroups(userId, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserGroupsGroups(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupsGroups(userGroups);
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserGroupsRelatedGroups(
		java.util.List<com.liferay.portal.model.UserGroup> userGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserGroupsRelatedGroups(userGroups);
	}

	public static java.util.List<com.liferay.portal.model.Group> getUserOrganizationsGroups(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getUserOrganizationsGroups(userId, start, end);
	}

	public static boolean hasRoleGroup(long roleId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasRoleGroup(roleId, groupId);
	}

	public static boolean hasStagingGroup(long liveGroupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasStagingGroup(liveGroupId);
	}

	public static boolean hasUserGroup(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserGroup(userId, groupId);
	}

	public static boolean hasUserGroup(long userId, long groupId,
		boolean inherit)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().hasUserGroup(userId, groupId, inherit);
	}

	public static java.util.List<com.liferay.portal.model.Group> search(
		long companyId, long[] classNameIds, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, classNameIds, name, description, params,
			start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> search(
		long companyId, long[] classNameIds, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, classNameIds, name, description, params,
			start, end, obc);
	}

	public static java.util.List<com.liferay.portal.model.Group> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, name, description, params, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Group> search(
		long companyId, java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params,
		int start, int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, name, description, params, start, end, obc);
	}

	public static int searchCount(long companyId, java.lang.String name,
		java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().searchCount(companyId, name, description, params);
	}

	public static int searchCount(long companyId, long[] classNameIds,
		java.lang.String name, java.lang.String description,
		java.util.LinkedHashMap<java.lang.String, java.lang.Object> params)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, classNameIds, name, description,
			params);
	}

	public static void setRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().setRoleGroups(roleId, groupIds);
	}

	public static void unsetRoleGroups(long roleId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unsetRoleGroups(roleId, groupIds);
	}

	public static void unsetUserGroups(long userId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().unsetUserGroups(userId, groupIds);
	}

	public static void updateAsset(long userId,
		com.liferay.portal.model.Group group, long[] assetCategoryIds,
		java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateAsset(userId, group, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.portal.model.Group updateFriendlyURL(
		long groupId, java.lang.String friendlyURL)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateFriendlyURL(groupId, friendlyURL);
	}

	public static com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateGroup(groupId, typeSettings);
	}

	public static com.liferay.portal.model.Group updateGroup(long groupId,
		java.lang.String name, java.lang.String description, int type,
		java.lang.String friendlyURL, boolean active,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateGroup(groupId, name, description, type, friendlyURL,
			active, serviceContext);
	}

	public static com.liferay.portal.model.Group updateSite(long groupId,
		boolean site)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSite(groupId, site);
	}

	public static GroupLocalService getService() {
		if (_service == null) {
			_service = (GroupLocalService)PortalBeanLocatorUtil.locate(GroupLocalService.class.getName());

			ReferenceRegistry.registerReference(GroupLocalServiceUtil.class,
				"_service");
			MethodCache.remove(GroupLocalService.class);
		}

		return _service;
	}

	public void setService(GroupLocalService service) {
		MethodCache.remove(GroupLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(GroupLocalServiceUtil.class,
			"_service");
		MethodCache.remove(GroupLocalService.class);
	}

	private static GroupLocalService _service;
}
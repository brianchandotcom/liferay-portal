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

package com.liferay.portlet.social.service;

/**
 * <p>
 * This class is a wrapper for {@link SocialActivityLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityLocalService
 * @generated
 */
public class SocialActivityLocalServiceWrapper
	implements SocialActivityLocalService {
	public SocialActivityLocalServiceWrapper(
		SocialActivityLocalService socialActivityLocalService) {
		_socialActivityLocalService = socialActivityLocalService;
	}

	/**
	* Adds the social activity to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivity the social activity
	* @return the social activity that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity addSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.addSocialActivity(socialActivity);
	}

	/**
	* Creates a new social activity with the primary key. Does not add the social activity to the database.
	*
	* @param activityId the primary key for the new social activity
	* @return the new social activity
	*/
	public com.liferay.portlet.social.model.SocialActivity createSocialActivity(
		long activityId) {
		return _socialActivityLocalService.createSocialActivity(activityId);
	}

	/**
	* Deletes the social activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityId the primary key of the social activity
	* @throws PortalException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialActivity(long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteSocialActivity(activityId);
	}

	/**
	* Deletes the social activity from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivity the social activity
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteSocialActivity(socialActivity);
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
		return _socialActivityLocalService.dynamicQuery(dynamicQuery);
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
		return _socialActivityLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _socialActivityLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _socialActivityLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the social activity with the primary key.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity
	* @throws PortalException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity getSocialActivity(
		long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getSocialActivity(activityId);
	}

	/**
	* Returns a range of all the social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getSocialActivities(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getSocialActivities(start, end);
	}

	/**
	* Returns the number of social activities.
	*
	* @return the number of social activities
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialActivitiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getSocialActivitiesCount();
	}

	/**
	* Updates the social activity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivity the social activity
	* @return the social activity that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity updateSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.updateSocialActivity(socialActivity);
	}

	/**
	* Updates the social activity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivity the social activity
	* @param merge whether to merge the social activity with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social activity that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivity updateSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.updateSocialActivity(socialActivity,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialActivityLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialActivityLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.social.model.SocialActivity addActivity(
		long userId, long groupId, java.util.Date createDate,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.addActivity(userId, groupId,
			createDate, className, classPK, type, extraData, receiverUserId);
	}

	public com.liferay.portlet.social.model.SocialActivity addActivity(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.addActivity(userId, groupId,
			className, classPK, type, extraData, receiverUserId);
	}

	public com.liferay.portlet.social.model.SocialActivity addUniqueActivity(
		long userId, long groupId, java.util.Date createDate,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.addUniqueActivity(userId, groupId,
			createDate, className, classPK, type, extraData, receiverUserId);
	}

	public com.liferay.portlet.social.model.SocialActivity addUniqueActivity(
		long userId, long groupId, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.addUniqueActivity(userId, groupId,
			className, classPK, type, extraData, receiverUserId);
	}

	public void deleteActivities(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteActivities(classNameId, classPK);
	}

	public void deleteActivities(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteActivities(className, classPK);
	}

	public void deleteActivity(long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteActivity(activityId);
	}

	public void deleteActivity(
		com.liferay.portlet.social.model.SocialActivity activity)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteActivity(activity);
	}

	public void deleteUserActivities(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteUserActivities(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivities(classNameId, start, end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, long classNameId, long classPK, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivities(mirrorActivityId,
			classNameId, classPK, start, end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, java.lang.String className, long classPK,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivities(mirrorActivityId,
			className, classPK, start, end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		java.lang.String className, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivities(className, start, end);
	}

	public int getActivitiesCount(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivitiesCount(classNameId);
	}

	public int getActivitiesCount(long mirrorActivityId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivitiesCount(mirrorActivityId,
			classNameId, classPK);
	}

	public int getActivitiesCount(long mirrorActivityId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivitiesCount(mirrorActivityId,
			className, classPK);
	}

	public int getActivitiesCount(java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivitiesCount(className);
	}

	public com.liferay.portlet.social.model.SocialActivity getActivity(
		long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivity(activityId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getGroupActivities(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getGroupActivities(groupId, start,
			end);
	}

	public int getGroupActivitiesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getGroupActivitiesCount(groupId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getGroupUsersActivities(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getGroupUsersActivities(groupId,
			start, end);
	}

	public int getGroupUsersActivitiesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getGroupUsersActivitiesCount(groupId);
	}

	public com.liferay.portlet.social.model.SocialActivity getMirrorActivity(
		long mirrorActivityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getMirrorActivity(mirrorActivityId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getOrganizationActivities(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getOrganizationActivities(organizationId,
			start, end);
	}

	public int getOrganizationActivitiesCount(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getOrganizationActivitiesCount(organizationId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getOrganizationUsersActivities(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getOrganizationUsersActivities(organizationId,
			start, end);
	}

	public int getOrganizationUsersActivitiesCount(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getOrganizationUsersActivitiesCount(organizationId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getRelationActivities(userId, start,
			end);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getRelationActivities(userId, type,
			start, end);
	}

	public int getRelationActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getRelationActivitiesCount(userId);
	}

	public int getRelationActivitiesCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getRelationActivitiesCount(userId,
			type);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserActivities(userId, start, end);
	}

	public int getUserActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserActivitiesCount(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserGroupsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserGroupsActivities(userId,
			start, end);
	}

	public int getUserGroupsActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserGroupsActivitiesCount(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserGroupsAndOrganizationsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserGroupsAndOrganizationsActivities(userId,
			start, end);
	}

	public int getUserGroupsAndOrganizationsActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserGroupsAndOrganizationsActivitiesCount(userId);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserOrganizationsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserOrganizationsActivities(userId,
			start, end);
	}

	public int getUserOrganizationsActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserOrganizationsActivitiesCount(userId);
	}

	public SocialActivityLocalService getWrappedSocialActivityLocalService() {
		return _socialActivityLocalService;
	}

	public void setWrappedSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {
		_socialActivityLocalService = socialActivityLocalService;
	}

	private SocialActivityLocalService _socialActivityLocalService;
}
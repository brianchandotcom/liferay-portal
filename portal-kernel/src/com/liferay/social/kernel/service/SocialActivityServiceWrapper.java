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

package com.liferay.social.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SocialActivityService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityService
 * @generated
 */
public class SocialActivityServiceWrapper
	implements ServiceWrapper<SocialActivityService>, SocialActivityService {

	public SocialActivityServiceWrapper(
		SocialActivityService socialActivityService) {

		_socialActivityService = socialActivityService;
	}

	/**
	 * Returns a range of all the activities done on assets identified by the
	 * class name ID.
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
	 * @param classNameId the target asset's class name ID
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getActivities(long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getActivities(classNameId, start, end);
	}

	/**
	 * Returns a range of all the activities done on the asset identified by the
	 * class name ID and class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
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
	 * @param mirrorActivityId the primary key of the mirror activity
	 * @param classNameId the target asset's class name ID
	 * @param classPK the primary key of the target asset
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getActivities(
				long mirrorActivityId, long classNameId, long classPK,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getActivities(
			mirrorActivityId, classNameId, classPK, start, end);
	}

	/**
	 * Returns a range of all the activities done on the asset identified by the
	 * class name and the class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
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
	 * @param mirrorActivityId the primary key of the mirror activity
	 * @param className the target asset's class name
	 * @param classPK the primary key of the target asset
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getActivities(
				long mirrorActivityId, String className, long classPK,
				int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getActivities(
			mirrorActivityId, className, classPK, start, end);
	}

	/**
	 * Returns a range of all the activities done on assets identified by the
	 * class name.
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
	 * @param className the target asset's class name
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getActivities(String className, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getActivities(className, start, end);
	}

	/**
	 * Returns the number of activities done on assets identified by the class
	 * name ID.
	 *
	 * @param classNameId the target asset's class name ID
	 * @return the number of matching activities
	 */
	@Override
	public int getActivitiesCount(long classNameId) {
		return _socialActivityService.getActivitiesCount(classNameId);
	}

	/**
	 * Returns the number of activities done on the asset identified by the
	 * class name ID and class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
	 *
	 * @param mirrorActivityId the primary key of the mirror activity
	 * @param classNameId the target asset's class name ID
	 * @param classPK the primary key of the target asset
	 * @return the number of matching activities
	 */
	@Override
	public int getActivitiesCount(
		long mirrorActivityId, long classNameId, long classPK) {

		return _socialActivityService.getActivitiesCount(
			mirrorActivityId, classNameId, classPK);
	}

	/**
	 * Returns the number of activities done on the asset identified by the
	 * class name and class primary key that are mirrors of the activity
	 * identified by the mirror activity ID.
	 *
	 * @param mirrorActivityId the primary key of the mirror activity
	 * @param className the target asset's class name
	 * @param classPK the primary key of the target asset
	 * @return the number of matching activities
	 */
	@Override
	public int getActivitiesCount(
		long mirrorActivityId, String className, long classPK) {

		return _socialActivityService.getActivitiesCount(
			mirrorActivityId, className, classPK);
	}

	/**
	 * Returns the number of activities done on assets identified by class name.
	 *
	 * @param className the target asset's class name
	 * @return the number of matching activities
	 */
	@Override
	public int getActivitiesCount(String className) {
		return _socialActivityService.getActivitiesCount(className);
	}

	/**
	 * Returns the activity identified by its primary key.
	 *
	 * @param activityId the primary key of the activity
	 * @return Returns the activity
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivity getActivity(
			long activityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getActivity(activityId);
	}

	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getActivitySetActivities(long activitySetId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getActivitySetActivities(
			activitySetId, start, end);
	}

	/**
	 * Returns a range of all the activities done in the group.
	 *
	 * <p>
	 * This method only finds activities without mirrors.
	 * </p>
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
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getGroupActivities(long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getGroupActivities(groupId, start, end);
	}

	/**
	 * Returns the number of activities done in the group.
	 *
	 * <p>
	 * This method only counts activities without mirrors.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @return the number of matching activities
	 */
	@Override
	public int getGroupActivitiesCount(long groupId) {
		return _socialActivityService.getGroupActivitiesCount(groupId);
	}

	/**
	 * Returns a range of activities done by users that are members of the
	 * group.
	 *
	 * <p>
	 * This method only finds activities without mirrors.
	 * </p>
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
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getGroupUsersActivities(long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getGroupUsersActivities(
			groupId, start, end);
	}

	/**
	 * Returns the number of activities done by users that are members of the
	 * group.
	 *
	 * <p>
	 * This method only counts activities without mirrors.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @return the number of matching activities
	 */
	@Override
	public int getGroupUsersActivitiesCount(long groupId) {
		return _socialActivityService.getGroupUsersActivitiesCount(groupId);
	}

	/**
	 * Returns the activity that has the mirror activity.
	 *
	 * @param mirrorActivityId the primary key of the mirror activity
	 * @return Returns the mirror activity
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivity getMirrorActivity(
			long mirrorActivityId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getMirrorActivity(mirrorActivityId);
	}

	/**
	 * Returns a range of all the activities done in the organization. This
	 * method only finds activities without mirrors.
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
	 * @param organizationId the primary key of the organization
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getOrganizationActivities(long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getOrganizationActivities(
			organizationId, start, end);
	}

	/**
	 * Returns the number of activities done in the organization. This method
	 * only counts activities without mirrors.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the number of matching activities
	 */
	@Override
	public int getOrganizationActivitiesCount(long organizationId) {
		return _socialActivityService.getOrganizationActivitiesCount(
			organizationId);
	}

	/**
	 * Returns a range of all the activities done by users of the organization.
	 * This method only finds activities without mirrors.
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
	 * @param organizationId the primary key of the organization
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getOrganizationUsersActivities(
				long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getOrganizationUsersActivities(
			organizationId, start, end);
	}

	/**
	 * Returns the number of activities done by users of the organization. This
	 * method only counts activities without mirrors.
	 *
	 * @param organizationId the primary key of the organization
	 * @return the number of matching activities
	 */
	@Override
	public int getOrganizationUsersActivitiesCount(long organizationId) {
		return _socialActivityService.getOrganizationUsersActivitiesCount(
			organizationId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _socialActivityService.getOSGiServiceIdentifier();
	}

	/**
	 * Returns a range of all the activities done by users in a relationship
	 * with the user identified by the user ID.
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
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getRelationActivities(long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getRelationActivities(userId, start, end);
	}

	/**
	 * Returns a range of all the activities done by users in a relationship of
	 * type <code>type</code> with the user identified by <code>userId</code>.
	 * This method only finds activities without mirrors.
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
	 * @param userId the primary key of the user
	 * @param type the relationship type
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getRelationActivities(long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getRelationActivities(
			userId, type, start, end);
	}

	/**
	 * Returns the number of activities done by users in a relationship with the
	 * user identified by userId.
	 *
	 * @param userId the primary key of the user
	 * @return the number of matching activities
	 */
	@Override
	public int getRelationActivitiesCount(long userId) {
		return _socialActivityService.getRelationActivitiesCount(userId);
	}

	/**
	 * Returns the number of activities done by users in a relationship of type
	 * <code>type</code> with the user identified by <code>userId</code>. This
	 * method only counts activities without mirrors.
	 *
	 * @param userId the primary key of the user
	 * @param type the relationship type
	 * @return the number of matching activities
	 */
	@Override
	public int getRelationActivitiesCount(long userId, int type) {
		return _socialActivityService.getRelationActivitiesCount(userId, type);
	}

	/**
	 * Returns a range of all the activities done by the user.
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
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getUserActivities(long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getUserActivities(userId, start, end);
	}

	/**
	 * Returns the number of activities done by the user.
	 *
	 * @param userId the primary key of the user
	 * @return the number of matching activities
	 */
	@Override
	public int getUserActivitiesCount(long userId) {
		return _socialActivityService.getUserActivitiesCount(userId);
	}

	/**
	 * Returns a range of all the activities done in the user's groups. This
	 * method only finds activities without mirrors.
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
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getUserGroupsActivities(long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getUserGroupsActivities(
			userId, start, end);
	}

	/**
	 * Returns the number of activities done in user's groups. This method only
	 * counts activities without mirrors.
	 *
	 * @param userId the primary key of the user
	 * @return the number of matching activities
	 */
	@Override
	public int getUserGroupsActivitiesCount(long userId) {
		return _socialActivityService.getUserGroupsActivitiesCount(userId);
	}

	/**
	 * Returns a range of all the activities done in the user's groups and
	 * organizations. This method only finds activities without mirrors.
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
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getUserGroupsAndOrganizationsActivities(
				long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getUserGroupsAndOrganizationsActivities(
			userId, start, end);
	}

	/**
	 * Returns the number of activities done in user's groups and organizations.
	 * This method only counts activities without mirrors.
	 *
	 * @param userId the primary key of the user
	 * @return the number of matching activities
	 */
	@Override
	public int getUserGroupsAndOrganizationsActivitiesCount(long userId) {
		return _socialActivityService.
			getUserGroupsAndOrganizationsActivitiesCount(userId);
	}

	/**
	 * Returns a range of all activities done in the user's organizations. This
	 * method only finds activities without mirrors.
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
	 * @param userId the primary key of the user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching activities
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivity>
			getUserOrganizationsActivities(long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityService.getUserOrganizationsActivities(
			userId, start, end);
	}

	/**
	 * Returns the number of activities done in the user's organizations. This
	 * method only counts activities without mirrors.
	 *
	 * @param userId the primary key of the user
	 * @return the number of matching activities
	 */
	@Override
	public int getUserOrganizationsActivitiesCount(long userId) {
		return _socialActivityService.getUserOrganizationsActivitiesCount(
			userId);
	}

	@Override
	public SocialActivityService getWrappedService() {
		return _socialActivityService;
	}

	@Override
	public void setWrappedService(SocialActivityService socialActivityService) {
		_socialActivityService = socialActivityService;
	}

	private SocialActivityService _socialActivityService;

}
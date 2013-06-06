/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SocialActivityLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityLocalService
 * @generated
 */
public class SocialActivityLocalServiceWrapper
	implements SocialActivityLocalService,
		ServiceWrapper<SocialActivityLocalService> {
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
	@Override
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
	@Override
	public com.liferay.portlet.social.model.SocialActivity createSocialActivity(
		long activityId) {
		return _socialActivityLocalService.createSocialActivity(activityId);
	}

	/**
	* Deletes the social activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity that was removed
	* @throws PortalException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivity deleteSocialActivity(
		long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.deleteSocialActivity(activityId);
	}

	/**
	* Deletes the social activity from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivity the social activity
	* @return the social activity that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivity deleteSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.deleteSocialActivity(socialActivity);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _socialActivityLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
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
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivity fetchSocialActivity(
		long activityId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.fetchSocialActivity(activityId);
	}

	/**
	* Returns the social activity with the primary key.
	*
	* @param activityId the primary key of the social activity
	* @return the social activity
	* @throws PortalException if a social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivity getSocialActivity(
		long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getSocialActivity(activityId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activities
	* @param end the upper bound of the range of social activities (not inclusive)
	* @return the range of social activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
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
	@Override
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
	@Override
	public com.liferay.portlet.social.model.SocialActivity updateSocialActivity(
		com.liferay.portlet.social.model.SocialActivity socialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.updateSocialActivity(socialActivity);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _socialActivityLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialActivityLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Records an activity with the given time in the database.
	*
	* <p>
	* This method records a social activity done on an asset, identified by its
	* class name and class primary key, in the database. Additional information
	* (such as the original message ID for a reply to a forum post) is passed
	* in via the <code>extraData</code> in JSON format. For activities
	* affecting another user, a mirror activity is generated that describes the
	* action from the user's point of view. The target user's ID is passed in
	* via the <code>receiverUserId</code>.
	* </p>
	*
	* <p>
	* Example for a mirrored activity:<br> When a user replies to a message
	* boards post, the reply action is stored in the database with the
	* <code>receiverUserId</code> being the ID of the author of the original
	* message. The <code>extraData</code> contains the ID of the original
	* message in JSON format. A mirror activity is generated with the values of
	* the <code>userId</code> and the <code>receiverUserId</code> swapped. This
	* mirror activity basically describes a "replied to" event.
	* </p>
	*
	* <p>
	* Mirror activities are most often used in relation to friend requests and
	* activities.
	* </p>
	*
	* @param userId the primary key of the acting user
	* @param groupId the primary key of the group
	* @param createDate the activity's date
	* @param className the target asset's class name
	* @param classPK the primary key of the target asset
	* @param type the activity's type
	* @param extraData any extra data regarding the activity
	* @param receiverUserId the primary key of the receiving user
	* @throws PortalException if the user or group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addActivity(long userId, long groupId,
		java.util.Date createDate, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.addActivity(userId, groupId, createDate,
			className, classPK, type, extraData, receiverUserId);
	}

	/**
	* Records an activity in the database, using a time based on the current
	* time in an attempt to make the activity's time unique.
	*
	* @param userId the primary key of the acting user
	* @param groupId the primary key of the group
	* @param className the target asset's class name
	* @param classPK the primary key of the target asset
	* @param type the activity's type
	* @param extraData any extra data regarding the activity
	* @param receiverUserId the primary key of the receiving user
	* @throws PortalException if the user or group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addActivity(long userId, long groupId,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.addActivity(userId, groupId, className,
			classPK, type, extraData, receiverUserId);
	}

	@Override
	public void addActivity(
		com.liferay.portlet.social.model.SocialActivity activity,
		com.liferay.portlet.social.model.SocialActivity mirrorActivity)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.addActivity(activity, mirrorActivity);
	}

	/**
	* Records an activity in the database, but only if there isn't already an
	* activity with the same parameters.
	*
	* <p>
	* For the main functionality see {@link #addActivity(long, long, Date,
	* String, long, int, String, long)}
	* </p>
	*
	* @param userId the primary key of the acting user
	* @param groupId the primary key of the group
	* @param createDate the activity's date
	* @param className the target asset's class name
	* @param classPK the primary key of the target asset
	* @param type the activity's type
	* @param extraData any extra data regarding the activity
	* @param receiverUserId the primary key of the receiving user
	* @throws PortalException if the user or group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addUniqueActivity(long userId, long groupId,
		java.util.Date createDate, java.lang.String className, long classPK,
		int type, java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.addUniqueActivity(userId, groupId,
			createDate, className, classPK, type, extraData, receiverUserId);
	}

	/**
	* Records an activity with the current time in the database, but only if
	* there isn't one with the same parameters.
	*
	* <p>
	* For the main functionality see {@link #addActivity(long, long, Date,
	* String, long, int, String, long)}
	* </p>
	*
	* @param userId the primary key of the acting user
	* @param groupId the primary key of the group
	* @param className the target asset's class name
	* @param classPK the primary key of the target asset
	* @param type the activity's type
	* @param extraData any extra data regarding the activity
	* @param receiverUserId the primary key of the receiving user
	* @throws PortalException if the user or group could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void addUniqueActivity(long userId, long groupId,
		java.lang.String className, long classPK, int type,
		java.lang.String extraData, long receiverUserId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.addUniqueActivity(userId, groupId,
			className, classPK, type, extraData, receiverUserId);
	}

	/**
	* Removes stored activities for the asset.
	*
	* @param assetEntry the asset from which to remove stored activities
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #deleteActivities(long)}
	*/
	@Override
	public void deleteActivities(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteActivities(assetEntry);
	}

	@Override
	public void deleteActivities(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteActivities(groupId);
	}

	/**
	* Removes stored activities for the asset identified by the class name and
	* class primary key.
	*
	* @param className the target asset's class name
	* @param classPK the primary key of the target asset
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #deleteActivities(long)}
	*/
	@Override
	public void deleteActivities(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteActivities(className, classPK);
	}

	/**
	* Removes the stored activity from the database.
	*
	* @param activityId the primary key of the stored activity
	* @throws PortalException if the activity could not be found
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #deleteActivities(long)}
	*/
	@Override
	public void deleteActivity(long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteActivity(activityId);
	}

	/**
	* Removes the stored activity and its mirror activity from the database.
	*
	* @param activity the activity to be removed
	* @throws SystemException if a system exception occurred
	* @deprecated As of 6.2.0, replaced by {@link #deleteActivities(long)}
	*/
	@Override
	public void deleteActivity(
		com.liferay.portlet.social.model.SocialActivity activity)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteActivity(activity);
	}

	/**
	* Removes the user's stored activities from the database.
	*
	* <p>
	* This method removes all activities where the user is either the actor or
	* the receiver.
	* </p>
	*
	* @param userId the primary key of the user
	* @throws PortalException if the user's activity counters could not be
	deleted
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public void deleteUserActivities(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityLocalService.deleteUserActivities(userId);
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivity fetchFirstActivity(
		java.lang.String className, long classPK, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.fetchFirstActivity(className,
			classPK, type);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param classNameId the target asset's class name ID
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivities(classNameId, start, end);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param mirrorActivityId the primary key of the mirror activity
	* @param classNameId the target asset's class name ID
	* @param classPK the primary key of the target asset
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, long classNameId, long classPK, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivities(mirrorActivityId,
			classNameId, classPK, start, end);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param mirrorActivityId the primary key of the mirror activity
	* @param className the target asset's class name
	* @param classPK the primary key of the target asset
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		long mirrorActivityId, java.lang.String className, long classPK,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivities(mirrorActivityId,
			className, classPK, start, end);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param className the target asset's class name
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivities(
		java.lang.String className, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivities(className, start, end);
	}

	/**
	* Returns the number of activities done on assets identified by the class
	* name ID.
	*
	* @param classNameId the target asset's class name ID
	* @return the number of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getActivitiesCount(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivitiesCount(classNameId);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getActivitiesCount(long mirrorActivityId, long classNameId,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivitiesCount(mirrorActivityId,
			classNameId, classPK);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getActivitiesCount(long mirrorActivityId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivitiesCount(mirrorActivityId,
			className, classPK);
	}

	/**
	* Returns the number of activities done on assets identified by class name.
	*
	* @param className the target asset's class name
	* @return the number of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getActivitiesCount(java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivitiesCount(className);
	}

	/**
	* Returns the activity identified by its primary key.
	*
	* @param activityId the primary key of the activity
	* @return Returns the activity
	* @throws PortalException if the activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivity getActivity(
		long activityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivity(activityId);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getActivitySetActivities(
		long activitySetId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getActivitySetActivities(activitySetId,
			start, end);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getGroupActivities(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getGroupActivities(groupId, start,
			end);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getGroupActivitiesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getGroupActivitiesCount(groupId);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getGroupUsersActivities(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getGroupUsersActivities(groupId,
			start, end);
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
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getGroupUsersActivitiesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getGroupUsersActivitiesCount(groupId);
	}

	/**
	* Returns the activity that has the mirror activity.
	*
	* @param mirrorActivityId the primary key of the mirror activity
	* @return Returns the mirror activity
	* @throws PortalException if the mirror activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivity getMirrorActivity(
		long mirrorActivityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getMirrorActivity(mirrorActivityId);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param organizationId the primary key of the organization
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getOrganizationActivities(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getOrganizationActivities(organizationId,
			start, end);
	}

	/**
	* Returns the number of activities done in the organization. This method
	* only counts activities without mirrors.
	*
	* @param organizationId the primary key of the organization
	* @return the number of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getOrganizationActivitiesCount(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getOrganizationActivitiesCount(organizationId);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param organizationId the primary key of the organization
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getOrganizationUsersActivities(
		long organizationId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getOrganizationUsersActivities(organizationId,
			start, end);
	}

	/**
	* Returns the number of activities done by users of the organization. This
	* method only counts activities without mirrors.
	*
	* @param organizationId the primary key of the organization
	* @return the number of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getOrganizationUsersActivitiesCount(long organizationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getOrganizationUsersActivitiesCount(organizationId);
	}

	/**
	* Returns a range of all the activities done by users in a relationship
	* with the user identified by the user ID.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <>0</code> refers
	* to the first result in the set. Setting both <code>start</code> and
	* <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param userId the primary key of the user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getRelationActivities(userId, start,
			end);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param userId the primary key of the user
	* @param type the relationship type
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getRelationActivities(
		long userId, int type, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getRelationActivities(userId, type,
			start, end);
	}

	/**
	* Returns the number of activities done by users in a relationship with the
	* user identified by userId.
	*
	* @param userId the primary key of the user
	* @return the number of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getRelationActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getRelationActivitiesCount(userId);
	}

	/**
	* Returns the number of activities done by users in a relationship of type
	* <code>type</code> with the user identified by <code>userId</code>. This
	* method only counts activities without mirrors.
	*
	* @param userId the primary key of the user
	* @param type the relationship type
	* @return the number of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getRelationActivitiesCount(long userId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getRelationActivitiesCount(userId,
			type);
	}

	/**
	* Returns a range of all the activities done by the user.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end -
	* start</code> instances. <code>start</code> and <code>end</code> are not
	* primary keys, they are indexes in the result set. Thus, <code>0</code>
	* refers to the first result in the set. Setting both <code>start</code>
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param userId the primary key of the user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserActivities(userId, start, end);
	}

	/**
	* Returns the number of activities done by the user.
	*
	* @param userId the primary key of the user
	* @return the number of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getUserActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserActivitiesCount(userId);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param userId the primary key of the user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserGroupsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserGroupsActivities(userId,
			start, end);
	}

	/**
	* Returns the number of activities done in user's groups. This method only
	* counts activities without mirrors.
	*
	* @param userId the primary key of the user
	* @return the number of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getUserGroupsActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserGroupsActivitiesCount(userId);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param userId the primary key of the user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserGroupsAndOrganizationsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserGroupsAndOrganizationsActivities(userId,
			start, end);
	}

	/**
	* Returns the number of activities done in user's groups and organizations.
	* This method only counts activities without mirrors.
	*
	* @param userId the primary key of the user
	* @return the number of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getUserGroupsAndOrganizationsActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserGroupsAndOrganizationsActivitiesCount(userId);
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
	* and <code>end</code> to {@link
	* com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full
	* result set.
	* </p>
	*
	* @param userId the primary key of the user
	* @param start the lower bound of the range of results
	* @param end the upper bound of the range of results (not inclusive)
	* @return the range of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> getUserOrganizationsActivities(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserOrganizationsActivities(userId,
			start, end);
	}

	/**
	* Returns the number of activities done in the user's organizations. This
	* method only counts activities without mirrors.
	*
	* @param userId the primary key of the user
	* @return the number of matching activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getUserOrganizationsActivitiesCount(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityLocalService.getUserOrganizationsActivitiesCount(userId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public SocialActivityLocalService getWrappedSocialActivityLocalService() {
		return _socialActivityLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {
		_socialActivityLocalService = socialActivityLocalService;
	}

	@Override
	public SocialActivityLocalService getWrappedService() {
		return _socialActivityLocalService;
	}

	@Override
	public void setWrappedService(
		SocialActivityLocalService socialActivityLocalService) {
		_socialActivityLocalService = socialActivityLocalService;
	}

	private SocialActivityLocalService _socialActivityLocalService;
}
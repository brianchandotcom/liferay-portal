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
 * Provides a wrapper for {@link SocialActivityCounterLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityCounterLocalService
 * @generated
 */
public class SocialActivityCounterLocalServiceWrapper
	implements ServiceWrapper<SocialActivityCounterLocalService>,
			   SocialActivityCounterLocalService {

	public SocialActivityCounterLocalServiceWrapper(
		SocialActivityCounterLocalService socialActivityCounterLocalService) {

		_socialActivityCounterLocalService = socialActivityCounterLocalService;
	}

	/**
	 * Adds an activity counter specifying a previous activity and period
	 * length.
	 *
	 * <p>
	 * This method uses the lock service to guard against multiple threads
	 * trying to insert the same counter because this service is called
	 * asynchronously from the social activity service.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the entity's class this counter
	 belongs to
	 * @param classPK the primary key of the entity this counter belongs to
	 * @param name the counter name
	 * @param ownerType the counter's owner type. Acceptable values are
	 <code>TYPE_ACTOR</code>, <code>TYPE_ASSET</code> and
	 <code>TYPE_CREATOR</code> defined in {@link
	 SocialActivityCounterConstants}.
	 * @param totalValue the counter's total value (optionally <code>0</code>)
	 * @param previousActivityCounterId the primary key of the activity counter
	 for the previous time period (optionally <code>0</code>, if this
	 is the first)
	 * @param periodLength the period length in days,
	 <code>PERIOD_LENGTH_INFINITE</code> for never ending counters or
	 <code>PERIOD_LENGTH_SYSTEM</code> for the period length defined
	 in <code>portal-ext.properties</code>. For more information see
	 {@link SocialActivityCounterConstants}.
	 * @return the added activity counter
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
			addActivityCounter(
				long groupId, long classNameId, long classPK, String name,
				int ownerType, int totalValue, long previousActivityCounterId,
				int periodLength)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityCounterLocalService.addActivityCounter(
			groupId, classNameId, classPK, name, ownerType, totalValue,
			previousActivityCounterId, periodLength);
	}

	/**
	 * Adds or increments activity counters related to an activity.
	 *
	 * <p>
	 * This method is called asynchronously from the social activity service
	 * when the user performs an activity defined in
	 * <code>liferay-social.xml</code>.
	 * </p>
	 *
	 * <p>
	 * This method first calls the activity processor class, if there is one
	 * defined for the activity, checks for limits and increments all the
	 * counters that belong to the activity. Afterwards, it processes the
	 * activity with respect to achievement classes, if any. Lastly it
	 * increments the built-in <code>user.activities</code> and
	 * <code>asset.activities</code> counters.
	 * </p>
	 *
	 * @param activity the social activity
	 */
	@Override
	public void addActivityCounters(
			com.liferay.social.kernel.model.SocialActivity activity)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivityCounterLocalService.addActivityCounters(activity);
	}

	/**
	 * Adds the social activity counter to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityCounterLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivityCounter the social activity counter
	 * @return the social activity counter that was added
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
		addSocialActivityCounter(
			com.liferay.social.kernel.model.SocialActivityCounter
				socialActivityCounter) {

		return _socialActivityCounterLocalService.addSocialActivityCounter(
			socialActivityCounter);
	}

	/**
	 * Creates a new social activity counter with the primary key. Does not add the social activity counter to the database.
	 *
	 * @param activityCounterId the primary key for the new social activity counter
	 * @return the new social activity counter
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
		createSocialActivityCounter(long activityCounterId) {

		return _socialActivityCounterLocalService.createSocialActivityCounter(
			activityCounterId);
	}

	/**
	 * Deletes all activity counters, limits, and settings related to the asset.
	 *
	 * <p>
	 * This method subtracts the asset's popularity from the owner's
	 * contribution points. It also creates a new contribution period if the
	 * latest one does not belong to the current period.
	 * </p>
	 *
	 * @param assetEntry the asset entry
	 */
	@Override
	public void deleteActivityCounters(
			com.liferay.asset.kernel.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivityCounterLocalService.deleteActivityCounters(assetEntry);
	}

	/**
	 * Deletes all activity counters, limits, and settings related to the entity
	 * identified by the class name ID and class primary key.
	 *
	 * @param classNameId the primary key of the entity's class
	 * @param classPK the primary key of the entity
	 */
	@Override
	public void deleteActivityCounters(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivityCounterLocalService.deleteActivityCounters(
			classNameId, classPK);
	}

	/**
	 * Deletes all activity counters for the entity identified by the class name
	 * and class primary key.
	 *
	 * @param className the entity's class name
	 * @param classPK the primary key of the entity
	 */
	@Override
	public void deleteActivityCounters(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivityCounterLocalService.deleteActivityCounters(
			className, classPK);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
			com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityCounterLocalService.deletePersistedModel(
			persistedModel);
	}

	/**
	 * Deletes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityCounterLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter that was removed
	 * @throws PortalException if a social activity counter with the primary key could not be found
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
			deleteSocialActivityCounter(long activityCounterId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityCounterLocalService.deleteSocialActivityCounter(
			activityCounterId);
	}

	/**
	 * Deletes the social activity counter from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityCounterLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivityCounter the social activity counter
	 * @return the social activity counter that was removed
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
		deleteSocialActivityCounter(
			com.liferay.social.kernel.model.SocialActivityCounter
				socialActivityCounter) {

		return _socialActivityCounterLocalService.deleteSocialActivityCounter(
			socialActivityCounter);
	}

	/**
	 * Disables all the counters of an asset identified by the class name ID and
	 * class primary key.
	 *
	 * <p>
	 * This method is used by the recycle bin to disable all counters of assets
	 * put into the recycle bin. It adjusts the owner's contribution score.
	 * </p>
	 *
	 * @param classNameId the primary key of the asset's class
	 * @param classPK the primary key of the asset
	 */
	@Override
	public void disableActivityCounters(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivityCounterLocalService.disableActivityCounters(
			classNameId, classPK);
	}

	/**
	 * Disables all the counters of an asset identified by the class name and
	 * class primary key.
	 *
	 * <p>
	 * This method is used by the recycle bin to disable all counters of assets
	 * put into the recycle bin. It adjusts the owner's contribution score.
	 * </p>
	 *
	 * @param className the asset's class name
	 * @param classPK the primary key of the asset
	 */
	@Override
	public void disableActivityCounters(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivityCounterLocalService.disableActivityCounters(
			className, classPK);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _socialActivityCounterLocalService.dynamicQuery();
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _socialActivityCounterLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {

		return _socialActivityCounterLocalService.dynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {

		return _socialActivityCounterLocalService.dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {

		return _socialActivityCounterLocalService.dynamicQueryCount(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {

		return _socialActivityCounterLocalService.dynamicQueryCount(
			dynamicQuery, projection);
	}

	/**
	 * Enables all activity counters of an asset identified by the class name ID
	 * and class primary key.
	 *
	 * <p>
	 * This method is used by the recycle bin to enable all counters of assets
	 * restored from the recycle bin. It adjusts the owner's contribution score.
	 * </p>
	 *
	 * @param classNameId the primary key of the asset's class
	 * @param classPK the primary key of the asset
	 */
	@Override
	public void enableActivityCounters(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivityCounterLocalService.enableActivityCounters(
			classNameId, classPK);
	}

	/**
	 * Enables all the counters of an asset identified by the class name and
	 * class primary key.
	 *
	 * <p>
	 * This method is used by the recycle bin to enable all counters of assets
	 * restored from the recycle bin. It adjusts the owner's contribution score.
	 * </p>
	 *
	 * @param className the asset's class name
	 * @param classPK the primary key of the asset
	 */
	@Override
	public void enableActivityCounters(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivityCounterLocalService.enableActivityCounters(
			className, classPK);
	}

	/**
	 * Returns the activity counter with the given name, owner, and end period
	 * that belong to the given entity.
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the entity's class
	 * @param classPK the primary key of the entity
	 * @param name the counter name
	 * @param ownerType the owner type
	 * @param endPeriod the end period, <code>-1</code> for the latest one
	 * @return the matching activity counter
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
		fetchActivityCounterByEndPeriod(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int endPeriod) {

		return _socialActivityCounterLocalService.
			fetchActivityCounterByEndPeriod(
				groupId, classNameId, classPK, name, ownerType, endPeriod);
	}

	/**
	 * Returns the activity counter with the given name, owner, and start period
	 * that belong to the given entity.
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the entity's class
	 * @param classPK the primary key of the entity
	 * @param name the counter name
	 * @param ownerType the owner type
	 * @param startPeriod the start period
	 * @return the matching activity counter
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
		fetchActivityCounterByStartPeriod(
			long groupId, long classNameId, long classPK, String name,
			int ownerType, int startPeriod) {

		return _socialActivityCounterLocalService.
			fetchActivityCounterByStartPeriod(
				groupId, classNameId, classPK, name, ownerType, startPeriod);
	}

	/**
	 * Returns the latest activity counter with the given name and owner that
	 * belong to the given entity.
	 *
	 * @param groupId the primary key of the group
	 * @param classNameId the primary key of the entity's class
	 * @param classPK the primary key of the entity
	 * @param name the counter name
	 * @param ownerType the owner type
	 * @return the matching activity counter
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
		fetchLatestActivityCounter(
			long groupId, long classNameId, long classPK, String name,
			int ownerType) {

		return _socialActivityCounterLocalService.fetchLatestActivityCounter(
			groupId, classNameId, classPK, name, ownerType);
	}

	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
		fetchSocialActivityCounter(long activityCounterId) {

		return _socialActivityCounterLocalService.fetchSocialActivityCounter(
			activityCounterId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return _socialActivityCounterLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		return _socialActivityCounterLocalService.
			getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns all the activity counters with the given name and period offsets.
	 *
	 * <p>
	 * The start and end offsets can belong to different periods. This method
	 * groups the counters by name and returns the sum of their current values.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param name the counter name
	 * @param startOffset the offset for the start period
	 * @param endOffset the offset for the end period
	 * @return the matching activity counters
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivityCounter>
		getOffsetActivityCounters(
			long groupId, String name, int startOffset, int endOffset) {

		return _socialActivityCounterLocalService.getOffsetActivityCounters(
			groupId, name, startOffset, endOffset);
	}

	/**
	 * Returns the distribution of the activity counters with the given name and
	 * period offsets.
	 *
	 * <p>
	 * The start and end offsets can belong to different periods. This method
	 * groups the counters by their owner entity (usually some asset) and
	 * returns a counter for each entity class with the sum of the counters'
	 * current values.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param name the counter name
	 * @param startOffset the offset for the start period
	 * @param endOffset the offset for the end period
	 * @return the distribution of matching activity counters
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivityCounter>
		getOffsetDistributionActivityCounters(
			long groupId, String name, int startOffset, int endOffset) {

		return _socialActivityCounterLocalService.
			getOffsetDistributionActivityCounters(
				groupId, name, startOffset, endOffset);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _socialActivityCounterLocalService.getOSGiServiceIdentifier();
	}

	/**
	 * Returns all the activity counters with the given name and time period.
	 *
	 * <p>
	 * The start and end period values can belong to different periods. This
	 * method groups the counters by name and returns the sum of their current
	 * values.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param name the counter name
	 * @param startPeriod the start period
	 * @param endPeriod the end period
	 * @return the matching activity counters
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivityCounter>
		getPeriodActivityCounters(
			long groupId, String name, int startPeriod, int endPeriod) {

		return _socialActivityCounterLocalService.getPeriodActivityCounters(
			groupId, name, startPeriod, endPeriod);
	}

	/**
	 * Returns the distribution of activity counters with the given name and
	 * time period.
	 *
	 * <p>
	 * The start and end period values can belong to different periods. This
	 * method groups the counters by their owner entity (usually some asset) and
	 * returns a counter for each entity class with the sum of the counters'
	 * current values.
	 * </p>
	 *
	 * @param groupId the primary key of the group
	 * @param name the counter name
	 * @param startPeriod the start period
	 * @param endPeriod the end period
	 * @return the distribution of matching activity counters
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivityCounter>
		getPeriodDistributionActivityCounters(
			long groupId, String name, int startPeriod, int endPeriod) {

		return _socialActivityCounterLocalService.
			getPeriodDistributionActivityCounters(
				groupId, name, startPeriod, endPeriod);
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
			java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityCounterLocalService.getPersistedModel(
			primaryKeyObj);
	}

	/**
	 * Returns the social activity counter with the primary key.
	 *
	 * @param activityCounterId the primary key of the social activity counter
	 * @return the social activity counter
	 * @throws PortalException if a social activity counter with the primary key could not be found
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
			getSocialActivityCounter(long activityCounterId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivityCounterLocalService.getSocialActivityCounter(
			activityCounterId);
	}

	/**
	 * Returns a range of all the social activity counters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivityCounterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity counters
	 * @param end the upper bound of the range of social activity counters (not inclusive)
	 * @return the range of social activity counters
	 */
	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivityCounter>
		getSocialActivityCounters(int start, int end) {

		return _socialActivityCounterLocalService.getSocialActivityCounters(
			start, end);
	}

	/**
	 * Returns the number of social activity counters.
	 *
	 * @return the number of social activity counters
	 */
	@Override
	public int getSocialActivityCountersCount() {
		return _socialActivityCounterLocalService.
			getSocialActivityCountersCount();
	}

	/**
	 * Returns the range of tuples that contain users and a list of activity
	 * counters.
	 *
	 * <p>
	 * The counters returned for each user are passed to this method in the
	 * selectedNames array. The method also accepts an array of counter names
	 * that are used to rank the users.
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
	 * @param rankingNames the ranking counter names
	 * @param selectedNames the counter names that will be returned with each
	 user
	 * @param start the lower bound of the range of results
	 * @param end the upper bound of the range of results (not inclusive)
	 * @return the range of matching tuples
	 */
	@Override
	public java.util.List<com.liferay.portal.kernel.util.Tuple>
		getUserActivityCounters(
			long groupId, String[] rankingNames, String[] selectedNames,
			int start, int end) {

		return _socialActivityCounterLocalService.getUserActivityCounters(
			groupId, rankingNames, selectedNames, start, end);
	}

	/**
	 * Returns the number of users having a rank based on the given counters.
	 *
	 * @param groupId the primary key of the group
	 * @param rankingNames the ranking counter names
	 * @return the number of matching users
	 */
	@Override
	public int getUserActivityCountersCount(
		long groupId, String[] rankingNames) {

		return _socialActivityCounterLocalService.getUserActivityCountersCount(
			groupId, rankingNames);
	}

	/**
	 * Increments the <code>user.achievements</code> counter for a user.
	 *
	 * <p>
	 * This method should be used by an external achievement class when the
	 * users unlocks an achievement.
	 * </p>
	 *
	 * @param userId the primary key of the user
	 * @param groupId the primary key of the group
	 */
	@Override
	public void incrementUserAchievementCounter(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivityCounterLocalService.incrementUserAchievementCounter(
			userId, groupId);
	}

	/**
	 * Updates the social activity counter in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivityCounterLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivityCounter the social activity counter
	 * @return the social activity counter that was updated
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityCounter
		updateSocialActivityCounter(
			com.liferay.social.kernel.model.SocialActivityCounter
				socialActivityCounter) {

		return _socialActivityCounterLocalService.updateSocialActivityCounter(
			socialActivityCounter);
	}

	@Override
	public SocialActivityCounterLocalService getWrappedService() {
		return _socialActivityCounterLocalService;
	}

	@Override
	public void setWrappedService(
		SocialActivityCounterLocalService socialActivityCounterLocalService) {

		_socialActivityCounterLocalService = socialActivityCounterLocalService;
	}

	private SocialActivityCounterLocalService
		_socialActivityCounterLocalService;

}
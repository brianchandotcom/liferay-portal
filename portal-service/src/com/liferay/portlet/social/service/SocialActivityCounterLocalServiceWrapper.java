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

package com.liferay.portlet.social.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SocialActivityCounterLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivityCounterLocalService
 * @generated
 */
public class SocialActivityCounterLocalServiceWrapper
	implements SocialActivityCounterLocalService,
		ServiceWrapper<SocialActivityCounterLocalService> {
	public SocialActivityCounterLocalServiceWrapper(
		SocialActivityCounterLocalService socialActivityCounterLocalService) {
		_socialActivityCounterLocalService = socialActivityCounterLocalService;
	}

	/**
	* Adds the social activity counter to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @return the social activity counter that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter addSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.addSocialActivityCounter(socialActivityCounter);
	}

	/**
	* Creates a new social activity counter with the primary key. Does not add the social activity counter to the database.
	*
	* @param activityCounterId the primary key for the new social activity counter
	* @return the new social activity counter
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter createSocialActivityCounter(
		long activityCounterId) {
		return _socialActivityCounterLocalService.createSocialActivityCounter(activityCounterId);
	}

	/**
	* Deletes the social activity counter with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter that was removed
	* @throws PortalException if a social activity counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter deleteSocialActivityCounter(
		long activityCounterId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.deleteSocialActivityCounter(activityCounterId);
	}

	/**
	* Deletes the social activity counter from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @return the social activity counter that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter deleteSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.deleteSocialActivityCounter(socialActivityCounter);
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
		return _socialActivityCounterLocalService.dynamicQuery(dynamicQuery);
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
		return _socialActivityCounterLocalService.dynamicQuery(dynamicQuery,
			start, end);
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
		return _socialActivityCounterLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _socialActivityCounterLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.social.model.SocialActivityCounter fetchSocialActivityCounter(
		long activityCounterId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.fetchSocialActivityCounter(activityCounterId);
	}

	/**
	* Returns the social activity counter with the primary key.
	*
	* @param activityCounterId the primary key of the social activity counter
	* @return the social activity counter
	* @throws PortalException if a social activity counter with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter getSocialActivityCounter(
		long activityCounterId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getSocialActivityCounter(activityCounterId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the social activity counters.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social activity counters
	* @param end the upper bound of the range of social activity counters (not inclusive)
	* @return the range of social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getSocialActivityCounters(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getSocialActivityCounters(start,
			end);
	}

	/**
	* Returns the number of social activity counters.
	*
	* @return the number of social activity counters
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialActivityCountersCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getSocialActivityCountersCount();
	}

	/**
	* Updates the social activity counter in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @return the social activity counter that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter updateSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.updateSocialActivityCounter(socialActivityCounter);
	}

	/**
	* Updates the social activity counter in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivityCounter the social activity counter
	* @param merge whether to merge the social activity counter with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social activity counter that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter updateSocialActivityCounter(
		com.liferay.portlet.social.model.SocialActivityCounter socialActivityCounter,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.updateSocialActivityCounter(socialActivityCounter,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialActivityCounterLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialActivityCounterLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.social.model.SocialActivityCounter addActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int currentValue, int totalValue, int startPeriod,
		int endPeriod)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.addActivityCounter(groupId,
			classNameId, classPK, name, ownerType, currentValue, totalValue,
			startPeriod, endPeriod);
	}

	public com.liferay.portlet.social.model.SocialActivityCounter addActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int currentValue, int totalValue, int startPeriod,
		int endPeriod, long previousActivityCounterId, int periodLength)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.addActivityCounter(groupId,
			classNameId, classPK, name, ownerType, currentValue, totalValue,
			startPeriod, endPeriod, previousActivityCounterId, periodLength);
	}

	public void addActivityCounters(
		com.liferay.portlet.social.model.SocialActivity activity)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.addActivityCounters(activity);
	}

	/**
	* @deprecated {@link #createActivityCounter(long, long, long, String, int,
	int, int, int, int, long, int)}
	*/
	public com.liferay.portlet.social.model.SocialActivityCounter createActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int currentValue, int totalValue, int startPeriod,
		int endPeriod)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.createActivityCounter(groupId,
			classNameId, classPK, name, ownerType, currentValue, totalValue,
			startPeriod, endPeriod);
	}

	public com.liferay.portlet.social.model.SocialActivityCounter createActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int currentValue, int totalValue, int startPeriod,
		int endPeriod, long previousActivityCounterId, int periodLength)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.createActivityCounter(groupId,
			classNameId, classPK, name, ownerType, currentValue, totalValue,
			startPeriod, endPeriod, previousActivityCounterId, periodLength);
	}

	public void deleteActivityCounters(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.deleteActivityCounters(assetEntry);
	}

	public void deleteActivityCounters(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.deleteActivityCounters(classNameId,
			classPK);
	}

	public void deleteActivityCounters(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.deleteActivityCounters(className,
			classPK);
	}

	public void disableActivityCounters(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.disableActivityCounters(classNameId,
			classPK);
	}

	public void disableActivityCounters(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.disableActivityCounters(className,
			classPK);
	}

	public void enableActivityCounters(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.enableActivityCounters(classNameId,
			classPK);
	}

	public void enableActivityCounters(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.enableActivityCounters(className,
			classPK);
	}

	public com.liferay.portlet.social.model.SocialActivityCounter fetchActivityCounterByEndPeriod(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.fetchActivityCounterByEndPeriod(groupId,
			classNameId, classPK, name, ownerType, endPeriod);
	}

	public com.liferay.portlet.social.model.SocialActivityCounter fetchActivityCounterByStartPeriod(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType, int startPeriod)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.fetchActivityCounterByStartPeriod(groupId,
			classNameId, classPK, name, ownerType, startPeriod);
	}

	public com.liferay.portlet.social.model.SocialActivityCounter fetchLatestActivityCounter(
		long groupId, long classNameId, long classPK, java.lang.String name,
		int ownerType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.fetchLatestActivityCounter(groupId,
			classNameId, classPK, name, ownerType);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getOffsetActivityCounters(
		long groupId, java.lang.String name, int startOffset, int endOffset)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getOffsetActivityCounters(groupId,
			name, startOffset, endOffset);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getOffsetDistributionActivityCounters(
		long groupId, java.lang.String name, int startOffset, int endOffset)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getOffsetDistributionActivityCounters(groupId,
			name, startOffset, endOffset);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getPeriodActivityCounters(
		long groupId, java.lang.String name, int startPeriod, int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getPeriodActivityCounters(groupId,
			name, startPeriod, endPeriod);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> getPeriodDistributionActivityCounters(
		long groupId, java.lang.String name, int startPeriod, int endPeriod)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getPeriodDistributionActivityCounters(groupId,
			name, startPeriod, endPeriod);
	}

	public java.util.List<com.liferay.portal.kernel.util.Tuple> getUserActivityCounters(
		long groupId, java.lang.String[] rankingNames,
		java.lang.String[] selectedNames, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getUserActivityCounters(groupId,
			rankingNames, selectedNames, start, end);
	}

	public int getUserActivityCountersCount(long groupId,
		java.lang.String[] rankingNames)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivityCounterLocalService.getUserActivityCountersCount(groupId,
			rankingNames);
	}

	public void incrementUserAchievementCounter(long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivityCounterLocalService.incrementUserAchievementCounter(userId,
			groupId);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SocialActivityCounterLocalService getWrappedSocialActivityCounterLocalService() {
		return _socialActivityCounterLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSocialActivityCounterLocalService(
		SocialActivityCounterLocalService socialActivityCounterLocalService) {
		_socialActivityCounterLocalService = socialActivityCounterLocalService;
	}

	public SocialActivityCounterLocalService getWrappedService() {
		return _socialActivityCounterLocalService;
	}

	public void setWrappedService(
		SocialActivityCounterLocalService socialActivityCounterLocalService) {
		_socialActivityCounterLocalService = socialActivityCounterLocalService;
	}

	private SocialActivityCounterLocalService _socialActivityCounterLocalService;
}
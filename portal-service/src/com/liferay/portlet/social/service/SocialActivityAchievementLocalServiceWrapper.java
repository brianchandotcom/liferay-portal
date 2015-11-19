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

package com.liferay.portlet.social.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SocialActivityAchievementLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivityAchievementLocalService
 * @generated
 */
@ProviderType
public class SocialActivityAchievementLocalServiceWrapper
	implements SocialActivityAchievementLocalService,
		ServiceWrapper<SocialActivityAchievementLocalService> {
	public SocialActivityAchievementLocalServiceWrapper(
		SocialActivityAchievementLocalService socialActivityAchievementLocalService) {
		_socialActivityAchievementLocalService = socialActivityAchievementLocalService;
	}

	@Override
	public void addActivityAchievement(long userId, long groupId,
		com.liferay.portlet.social.model.SocialAchievement achievement)
		throws com.liferay.portal.kernel.exception.PortalException {
		_socialActivityAchievementLocalService.addActivityAchievement(userId,
			groupId, achievement);
	}

	/**
	* Adds the social activity achievement to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityAchievement the social activity achievement
	* @return the social activity achievement that was added
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivityAchievement addSocialActivityAchievement(
		com.liferay.portlet.social.model.SocialActivityAchievement socialActivityAchievement) {
		return _socialActivityAchievementLocalService.addSocialActivityAchievement(socialActivityAchievement);
	}

	/**
	* Creates a new social activity achievement with the primary key. Does not add the social activity achievement to the database.
	*
	* @param activityAchievementId the primary key for the new social activity achievement
	* @return the new social activity achievement
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivityAchievement createSocialActivityAchievement(
		long activityAchievementId) {
		return _socialActivityAchievementLocalService.createSocialActivityAchievement(activityAchievementId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivityAchievementLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the social activity achievement with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activityAchievementId the primary key of the social activity achievement
	* @return the social activity achievement that was removed
	* @throws PortalException if a social activity achievement with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivityAchievement deleteSocialActivityAchievement(
		long activityAchievementId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivityAchievementLocalService.deleteSocialActivityAchievement(activityAchievementId);
	}

	/**
	* Deletes the social activity achievement from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivityAchievement the social activity achievement
	* @return the social activity achievement that was removed
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivityAchievement deleteSocialActivityAchievement(
		com.liferay.portlet.social.model.SocialActivityAchievement socialActivityAchievement) {
		return _socialActivityAchievementLocalService.deleteSocialActivityAchievement(socialActivityAchievement);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _socialActivityAchievementLocalService.dynamicQuery();
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
		return _socialActivityAchievementLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _socialActivityAchievementLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _socialActivityAchievementLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _socialActivityAchievementLocalService.dynamicQueryCount(dynamicQuery);
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
		return _socialActivityAchievementLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivityAchievement fetchSocialActivityAchievement(
		long activityAchievementId) {
		return _socialActivityAchievementLocalService.fetchSocialActivityAchievement(activityAchievementId);
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivityAchievement fetchUserAchievement(
		long userId, long groupId, java.lang.String name) {
		return _socialActivityAchievementLocalService.fetchUserAchievement(userId,
			groupId, name);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _socialActivityAchievementLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> getGroupAchievements(
		long groupId) {
		return _socialActivityAchievementLocalService.getGroupAchievements(groupId);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> getGroupAchievements(
		long groupId, java.lang.String name) {
		return _socialActivityAchievementLocalService.getGroupAchievements(groupId,
			name);
	}

	@Override
	public int getGroupAchievementsCount(long groupId) {
		return _socialActivityAchievementLocalService.getGroupAchievementsCount(groupId);
	}

	@Override
	public int getGroupAchievementsCount(long groupId, java.lang.String name) {
		return _socialActivityAchievementLocalService.getGroupAchievementsCount(groupId,
			name);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> getGroupFirstAchievements(
		long groupId) {
		return _socialActivityAchievementLocalService.getGroupFirstAchievements(groupId);
	}

	@Override
	public int getGroupFirstAchievementsCount(long groupId) {
		return _socialActivityAchievementLocalService.getGroupFirstAchievementsCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _socialActivityAchievementLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _socialActivityAchievementLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivityAchievementLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the social activity achievement with the primary key.
	*
	* @param activityAchievementId the primary key of the social activity achievement
	* @return the social activity achievement
	* @throws PortalException if a social activity achievement with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivityAchievement getSocialActivityAchievement(
		long activityAchievementId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivityAchievementLocalService.getSocialActivityAchievement(activityAchievementId);
	}

	/**
	* Returns a range of all the social activity achievements.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivityAchievementModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity achievements
	* @param end the upper bound of the range of social activity achievements (not inclusive)
	* @return the range of social activity achievements
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> getSocialActivityAchievements(
		int start, int end) {
		return _socialActivityAchievementLocalService.getSocialActivityAchievements(start,
			end);
	}

	/**
	* Returns the number of social activity achievements.
	*
	* @return the number of social activity achievements
	*/
	@Override
	public int getSocialActivityAchievementsCount() {
		return _socialActivityAchievementLocalService.getSocialActivityAchievementsCount();
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivityAchievement> getUserAchievements(
		long userId, long groupId) {
		return _socialActivityAchievementLocalService.getUserAchievements(userId,
			groupId);
	}

	@Override
	public int getUserAchievementsCount(long userId, long groupId) {
		return _socialActivityAchievementLocalService.getUserAchievementsCount(userId,
			groupId);
	}

	/**
	* Updates the social activity achievement in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivityAchievement the social activity achievement
	* @return the social activity achievement that was updated
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivityAchievement updateSocialActivityAchievement(
		com.liferay.portlet.social.model.SocialActivityAchievement socialActivityAchievement) {
		return _socialActivityAchievementLocalService.updateSocialActivityAchievement(socialActivityAchievement);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SocialActivityAchievementLocalService getWrappedSocialActivityAchievementLocalService() {
		return _socialActivityAchievementLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSocialActivityAchievementLocalService(
		SocialActivityAchievementLocalService socialActivityAchievementLocalService) {
		_socialActivityAchievementLocalService = socialActivityAchievementLocalService;
	}

	@Override
	public SocialActivityAchievementLocalService getWrappedService() {
		return _socialActivityAchievementLocalService;
	}

	@Override
	public void setWrappedService(
		SocialActivityAchievementLocalService socialActivityAchievementLocalService) {
		_socialActivityAchievementLocalService = socialActivityAchievementLocalService;
	}

	private SocialActivityAchievementLocalService _socialActivityAchievementLocalService;
}
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
 * Provides a wrapper for {@link SocialActivitySetLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySetLocalService
 * @generated
 */
@ProviderType
public class SocialActivitySetLocalServiceWrapper
	implements SocialActivitySetLocalService,
		ServiceWrapper<SocialActivitySetLocalService> {
	public SocialActivitySetLocalServiceWrapper(
		SocialActivitySetLocalService socialActivitySetLocalService) {
		_socialActivitySetLocalService = socialActivitySetLocalService;
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivitySet addActivitySet(
		long activityId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivitySetLocalService.addActivitySet(activityId);
	}

	/**
	* Adds the social activity set to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySet the social activity set
	* @return the social activity set that was added
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivitySet addSocialActivitySet(
		com.liferay.portlet.social.model.SocialActivitySet socialActivitySet) {
		return _socialActivitySetLocalService.addSocialActivitySet(socialActivitySet);
	}

	/**
	* Creates a new social activity set with the primary key. Does not add the social activity set to the database.
	*
	* @param activitySetId the primary key for the new social activity set
	* @return the new social activity set
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivitySet createSocialActivitySet(
		long activitySetId) {
		return _socialActivitySetLocalService.createSocialActivitySet(activitySetId);
	}

	@Override
	public void decrementActivityCount(long activitySetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_socialActivitySetLocalService.decrementActivityCount(activitySetId);
	}

	@Override
	public void decrementActivityCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		_socialActivitySetLocalService.decrementActivityCount(classNameId,
			classPK);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivitySetLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activitySetId the primary key of the social activity set
	* @return the social activity set that was removed
	* @throws PortalException if a social activity set with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivitySet deleteSocialActivitySet(
		long activitySetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivitySetLocalService.deleteSocialActivitySet(activitySetId);
	}

	/**
	* Deletes the social activity set from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySet the social activity set
	* @return the social activity set that was removed
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivitySet deleteSocialActivitySet(
		com.liferay.portlet.social.model.SocialActivitySet socialActivitySet) {
		return _socialActivitySetLocalService.deleteSocialActivitySet(socialActivitySet);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _socialActivitySetLocalService.dynamicQuery();
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
		return _socialActivitySetLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _socialActivitySetLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _socialActivitySetLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _socialActivitySetLocalService.dynamicQueryCount(dynamicQuery);
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
		return _socialActivitySetLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivitySet fetchSocialActivitySet(
		long activitySetId) {
		return _socialActivitySetLocalService.fetchSocialActivitySet(activitySetId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _socialActivitySetLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivitySet getClassActivitySet(
		long classNameId, long classPK, int type) {
		return _socialActivitySetLocalService.getClassActivitySet(classNameId,
			classPK, type);
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivitySet getClassActivitySet(
		long userId, long classNameId, long classPK, int type) {
		return _socialActivitySetLocalService.getClassActivitySet(userId,
			classNameId, classPK, type);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getGroupActivitySets(
		long groupId, int start, int end) {
		return _socialActivitySetLocalService.getGroupActivitySets(groupId,
			start, end);
	}

	@Override
	public int getGroupActivitySetsCount(long groupId) {
		return _socialActivitySetLocalService.getGroupActivitySetsCount(groupId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _socialActivitySetLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _socialActivitySetLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivitySetLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getRelationActivitySets(
		long userId, int start, int end) {
		return _socialActivitySetLocalService.getRelationActivitySets(userId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getRelationActivitySets(
		long userId, int type, int start, int end) {
		return _socialActivitySetLocalService.getRelationActivitySets(userId,
			type, start, end);
	}

	@Override
	public int getRelationActivitySetsCount(long userId) {
		return _socialActivitySetLocalService.getRelationActivitySetsCount(userId);
	}

	@Override
	public int getRelationActivitySetsCount(long userId, int type) {
		return _socialActivitySetLocalService.getRelationActivitySetsCount(userId,
			type);
	}

	/**
	* Returns the social activity set with the primary key.
	*
	* @param activitySetId the primary key of the social activity set
	* @return the social activity set
	* @throws PortalException if a social activity set with the primary key could not be found
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivitySet getSocialActivitySet(
		long activitySetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _socialActivitySetLocalService.getSocialActivitySet(activitySetId);
	}

	/**
	* Returns a range of all the social activity sets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity sets
	* @param end the upper bound of the range of social activity sets (not inclusive)
	* @return the range of social activity sets
	*/
	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getSocialActivitySets(
		int start, int end) {
		return _socialActivitySetLocalService.getSocialActivitySets(start, end);
	}

	/**
	* Returns the number of social activity sets.
	*
	* @return the number of social activity sets
	*/
	@Override
	public int getSocialActivitySetsCount() {
		return _socialActivitySetLocalService.getSocialActivitySetsCount();
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivitySet getUserActivitySet(
		long groupId, long userId, long classNameId, int type) {
		return _socialActivitySetLocalService.getUserActivitySet(groupId,
			userId, classNameId, type);
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivitySet getUserActivitySet(
		long groupId, long userId, int type) {
		return _socialActivitySetLocalService.getUserActivitySet(groupId,
			userId, type);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getUserActivitySets(
		long userId, int start, int end) {
		return _socialActivitySetLocalService.getUserActivitySets(userId,
			start, end);
	}

	@Override
	public int getUserActivitySetsCount(long userId) {
		return _socialActivitySetLocalService.getUserActivitySetsCount(userId);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getUserGroupsActivitySets(
		long userId, int start, int end) {
		return _socialActivitySetLocalService.getUserGroupsActivitySets(userId,
			start, end);
	}

	@Override
	public int getUserGroupsActivitySetsCount(long userId) {
		return _socialActivitySetLocalService.getUserGroupsActivitySetsCount(userId);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getUserViewableActivitySets(
		long userId, int start, int end) {
		return _socialActivitySetLocalService.getUserViewableActivitySets(userId,
			start, end);
	}

	@Override
	public int getUserViewableActivitySetsCount(long userId) {
		return _socialActivitySetLocalService.getUserViewableActivitySetsCount(userId);
	}

	@Override
	public void incrementActivityCount(long activitySetId, long activityId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_socialActivitySetLocalService.incrementActivityCount(activitySetId,
			activityId);
	}

	/**
	* Updates the social activity set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySet the social activity set
	* @return the social activity set that was updated
	*/
	@Override
	public com.liferay.portlet.social.model.SocialActivitySet updateSocialActivitySet(
		com.liferay.portlet.social.model.SocialActivitySet socialActivitySet) {
		return _socialActivitySetLocalService.updateSocialActivitySet(socialActivitySet);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public SocialActivitySetLocalService getWrappedSocialActivitySetLocalService() {
		return _socialActivitySetLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedSocialActivitySetLocalService(
		SocialActivitySetLocalService socialActivitySetLocalService) {
		_socialActivitySetLocalService = socialActivitySetLocalService;
	}

	@Override
	public SocialActivitySetLocalService getWrappedService() {
		return _socialActivitySetLocalService;
	}

	@Override
	public void setWrappedService(
		SocialActivitySetLocalService socialActivitySetLocalService) {
		_socialActivitySetLocalService = socialActivitySetLocalService;
	}

	private SocialActivitySetLocalService _socialActivitySetLocalService;
}
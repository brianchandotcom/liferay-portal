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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for SocialActivitySet. This utility wraps
 * {@link com.liferay.portlet.social.service.impl.SocialActivitySetLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySetLocalService
 * @see com.liferay.portlet.social.service.base.SocialActivitySetLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.impl.SocialActivitySetLocalServiceImpl
 * @generated
 */
@ProviderType
public class SocialActivitySetLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.social.service.impl.SocialActivitySetLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portlet.social.model.SocialActivitySet addActivitySet(
		long activityId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addActivitySet(activityId);
	}

	/**
	* Adds the social activity set to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySet the social activity set
	* @return the social activity set that was added
	*/
	public static com.liferay.portlet.social.model.SocialActivitySet addSocialActivitySet(
		com.liferay.portlet.social.model.SocialActivitySet socialActivitySet) {
		return getService().addSocialActivitySet(socialActivitySet);
	}

	/**
	* Creates a new social activity set with the primary key. Does not add the social activity set to the database.
	*
	* @param activitySetId the primary key for the new social activity set
	* @return the new social activity set
	*/
	public static com.liferay.portlet.social.model.SocialActivitySet createSocialActivitySet(
		long activitySetId) {
		return getService().createSocialActivitySet(activitySetId);
	}

	public static void decrementActivityCount(long activitySetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().decrementActivityCount(activitySetId);
	}

	public static void decrementActivityCount(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().decrementActivityCount(classNameId, classPK);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the social activity set with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activitySetId the primary key of the social activity set
	* @return the social activity set that was removed
	* @throws PortalException if a social activity set with the primary key could not be found
	*/
	public static com.liferay.portlet.social.model.SocialActivitySet deleteSocialActivitySet(
		long activitySetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteSocialActivitySet(activitySetId);
	}

	/**
	* Deletes the social activity set from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySet the social activity set
	* @return the social activity set that was removed
	*/
	public static com.liferay.portlet.social.model.SocialActivitySet deleteSocialActivitySet(
		com.liferay.portlet.social.model.SocialActivitySet socialActivitySet) {
		return getService().deleteSocialActivitySet(socialActivitySet);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySetModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.social.model.SocialActivitySet fetchSocialActivitySet(
		long activitySetId) {
		return getService().fetchSocialActivitySet(activitySetId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portlet.social.model.SocialActivitySet getClassActivitySet(
		long classNameId, long classPK, int type) {
		return getService().getClassActivitySet(classNameId, classPK, type);
	}

	public static com.liferay.portlet.social.model.SocialActivitySet getClassActivitySet(
		long userId, long classNameId, long classPK, int type) {
		return getService()
				   .getClassActivitySet(userId, classNameId, classPK, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getGroupActivitySets(
		long groupId, int start, int end) {
		return getService().getGroupActivitySets(groupId, start, end);
	}

	public static int getGroupActivitySetsCount(long groupId) {
		return getService().getGroupActivitySetsCount(groupId);
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

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getRelationActivitySets(
		long userId, int start, int end) {
		return getService().getRelationActivitySets(userId, start, end);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getRelationActivitySets(
		long userId, int type, int start, int end) {
		return getService().getRelationActivitySets(userId, type, start, end);
	}

	public static int getRelationActivitySetsCount(long userId) {
		return getService().getRelationActivitySetsCount(userId);
	}

	public static int getRelationActivitySetsCount(long userId, int type) {
		return getService().getRelationActivitySetsCount(userId, type);
	}

	/**
	* Returns the social activity set with the primary key.
	*
	* @param activitySetId the primary key of the social activity set
	* @return the social activity set
	* @throws PortalException if a social activity set with the primary key could not be found
	*/
	public static com.liferay.portlet.social.model.SocialActivitySet getSocialActivitySet(
		long activitySetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getSocialActivitySet(activitySetId);
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
	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getSocialActivitySets(
		int start, int end) {
		return getService().getSocialActivitySets(start, end);
	}

	/**
	* Returns the number of social activity sets.
	*
	* @return the number of social activity sets
	*/
	public static int getSocialActivitySetsCount() {
		return getService().getSocialActivitySetsCount();
	}

	public static com.liferay.portlet.social.model.SocialActivitySet getUserActivitySet(
		long groupId, long userId, long classNameId, int type) {
		return getService()
				   .getUserActivitySet(groupId, userId, classNameId, type);
	}

	public static com.liferay.portlet.social.model.SocialActivitySet getUserActivitySet(
		long groupId, long userId, int type) {
		return getService().getUserActivitySet(groupId, userId, type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getUserActivitySets(
		long userId, int start, int end) {
		return getService().getUserActivitySets(userId, start, end);
	}

	public static int getUserActivitySetsCount(long userId) {
		return getService().getUserActivitySetsCount(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getUserGroupsActivitySets(
		long userId, int start, int end) {
		return getService().getUserGroupsActivitySets(userId, start, end);
	}

	public static int getUserGroupsActivitySetsCount(long userId) {
		return getService().getUserGroupsActivitySetsCount(userId);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> getUserViewableActivitySets(
		long userId, int start, int end) {
		return getService().getUserViewableActivitySets(userId, start, end);
	}

	public static int getUserViewableActivitySetsCount(long userId) {
		return getService().getUserViewableActivitySetsCount(userId);
	}

	public static void incrementActivityCount(long activitySetId,
		long activityId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().incrementActivityCount(activitySetId, activityId);
	}

	/**
	* Updates the social activity set in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySet the social activity set
	* @return the social activity set that was updated
	*/
	public static com.liferay.portlet.social.model.SocialActivitySet updateSocialActivitySet(
		com.liferay.portlet.social.model.SocialActivitySet socialActivitySet) {
		return getService().updateSocialActivitySet(socialActivitySet);
	}

	public static SocialActivitySetLocalService getService() {
		if (_service == null) {
			_service = (SocialActivitySetLocalService)PortalBeanLocatorUtil.locate(SocialActivitySetLocalService.class.getName());

			ReferenceRegistry.registerReference(SocialActivitySetLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(SocialActivitySetLocalService service) {
	}

	private static SocialActivitySetLocalService _service;
}
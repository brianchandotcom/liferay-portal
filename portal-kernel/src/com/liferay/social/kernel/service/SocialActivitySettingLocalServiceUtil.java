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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the local service utility for SocialActivitySetting. This utility wraps
 * <code>com.liferay.portlet.social.service.impl.SocialActivitySettingLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySettingLocalService
 * @generated
 */
public class SocialActivitySettingLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.social.service.impl.SocialActivitySettingLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Adds the social activity setting to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivitySettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivitySetting the social activity setting
	 * @return the social activity setting that was added
	 */
	public static com.liferay.social.kernel.model.SocialActivitySetting
		addSocialActivitySetting(
			com.liferay.social.kernel.model.SocialActivitySetting
				socialActivitySetting) {

		return getService().addSocialActivitySetting(socialActivitySetting);
	}

	/**
	 * Creates a new social activity setting with the primary key. Does not add the social activity setting to the database.
	 *
	 * @param activitySettingId the primary key for the new social activity setting
	 * @return the new social activity setting
	 */
	public static com.liferay.social.kernel.model.SocialActivitySetting
		createSocialActivitySetting(long activitySettingId) {

		return getService().createSocialActivitySetting(activitySettingId);
	}

	public static void deleteActivitySetting(
		long groupId, String className, long classPK) {

		getService().deleteActivitySetting(groupId, className, classPK);
	}

	public static void deleteActivitySettings(long groupId) {
		getService().deleteActivitySettings(groupId);
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			deletePersistedModel(
				com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deletePersistedModel(persistedModel);
	}

	/**
	 * Deletes the social activity setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivitySettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param activitySettingId the primary key of the social activity setting
	 * @return the social activity setting that was removed
	 * @throws PortalException if a social activity setting with the primary key could not be found
	 */
	public static com.liferay.social.kernel.model.SocialActivitySetting
			deleteSocialActivitySetting(long activitySettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteSocialActivitySetting(activitySettingId);
	}

	/**
	 * Deletes the social activity setting from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivitySettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivitySetting the social activity setting
	 * @return the social activity setting that was removed
	 */
	public static com.liferay.social.kernel.model.SocialActivitySetting
		deleteSocialActivitySetting(
			com.liferay.social.kernel.model.SocialActivitySetting
				socialActivitySetting) {

		return getService().deleteSocialActivitySetting(socialActivitySetting);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery
		dynamicQuery() {

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl</code>.
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

		return getService().dynamicQuery(
			dynamicQuery, start, end, orderByComparator);
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

	public static com.liferay.social.kernel.model.SocialActivitySetting
		fetchSocialActivitySetting(long activitySettingId) {

		return getService().fetchSocialActivitySetting(activitySettingId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery
		getActionableDynamicQuery() {

		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.social.kernel.model.SocialActivityDefinition
		getActivityDefinition(
			long groupId, String className, int activityType) {

		return getService().getActivityDefinition(
			groupId, className, activityType);
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivityDefinition>
			getActivityDefinitions(long groupId, String className) {

		return getService().getActivityDefinitions(groupId, className);
	}

	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivitySetting>
			getActivitySettings(long groupId) {

		return getService().getActivitySettings(groupId);
	}

	public static
		com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery
			getIndexableActionableDynamicQuery() {

		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * @throws PortalException
	 */
	public static com.liferay.portal.kernel.model.PersistedModel
			getPersistedModel(java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	 * Returns the social activity setting with the primary key.
	 *
	 * @param activitySettingId the primary key of the social activity setting
	 * @return the social activity setting
	 * @throws PortalException if a social activity setting with the primary key could not be found
	 */
	public static com.liferay.social.kernel.model.SocialActivitySetting
			getSocialActivitySetting(long activitySettingId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getSocialActivitySetting(activitySettingId);
	}

	/**
	 * Returns a range of all the social activity settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of social activity settings
	 * @param end the upper bound of the range of social activity settings (not inclusive)
	 * @return the range of social activity settings
	 */
	public static java.util.List
		<com.liferay.social.kernel.model.SocialActivitySetting>
			getSocialActivitySettings(int start, int end) {

		return getService().getSocialActivitySettings(start, end);
	}

	/**
	 * Returns the number of social activity settings.
	 *
	 * @return the number of social activity settings
	 */
	public static int getSocialActivitySettingsCount() {
		return getService().getSocialActivitySettingsCount();
	}

	public static boolean isEnabled(long groupId, long classNameId) {
		return getService().isEnabled(groupId, classNameId);
	}

	public static boolean isEnabled(
		long groupId, long classNameId, long classPK) {

		return getService().isEnabled(groupId, classNameId, classPK);
	}

	public static void updateActivitySetting(
			long groupId, String className, boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateActivitySetting(groupId, className, enabled);
	}

	public static void updateActivitySetting(
			long groupId, String className, int activityType,
			com.liferay.social.kernel.model.SocialActivityCounterDefinition
				activityCounterDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateActivitySetting(
			groupId, className, activityType, activityCounterDefinition);
	}

	public static void updateActivitySetting(
			long groupId, String className, long classPK, boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateActivitySetting(
			groupId, className, classPK, enabled);
	}

	public static void updateActivitySettings(
			long groupId, String className, int activityType,
			java.util.List
				<com.liferay.social.kernel.model.
					SocialActivityCounterDefinition> activityCounterDefinitions)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().updateActivitySettings(
			groupId, className, activityType, activityCounterDefinitions);
	}

	/**
	 * Updates the social activity setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect SocialActivitySettingLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param socialActivitySetting the social activity setting
	 * @return the social activity setting that was updated
	 */
	public static com.liferay.social.kernel.model.SocialActivitySetting
		updateSocialActivitySetting(
			com.liferay.social.kernel.model.SocialActivitySetting
				socialActivitySetting) {

		return getService().updateSocialActivitySetting(socialActivitySetting);
	}

	public static SocialActivitySettingLocalService getService() {
		if (_service == null) {
			_service =
				(SocialActivitySettingLocalService)PortalBeanLocatorUtil.locate(
					SocialActivitySettingLocalService.class.getName());
		}

		return _service;
	}

	private static SocialActivitySettingLocalService _service;

}
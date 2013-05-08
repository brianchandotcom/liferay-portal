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
 * Provides a wrapper for {@link SocialActivitySettingLocalService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialActivitySettingLocalService
 * @generated
 */
public class SocialActivitySettingLocalServiceWrapper
	implements SocialActivitySettingLocalService,
		ServiceWrapper<SocialActivitySettingLocalService> {
	public SocialActivitySettingLocalServiceWrapper(
		SocialActivitySettingLocalService socialActivitySettingLocalService) {
		_socialActivitySettingLocalService = socialActivitySettingLocalService;
	}

	/**
	* Adds the social activity setting to the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySetting the social activity setting
	* @return the social activity setting that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivitySetting addSocialActivitySetting(
		com.liferay.portlet.social.model.SocialActivitySetting socialActivitySetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.addSocialActivitySetting(socialActivitySetting);
	}

	/**
	* Creates a new social activity setting with the primary key. Does not add the social activity setting to the database.
	*
	* @param activitySettingId the primary key for the new social activity setting
	* @return the new social activity setting
	*/
	public com.liferay.portlet.social.model.SocialActivitySetting createSocialActivitySetting(
		long activitySettingId) {
		return _socialActivitySettingLocalService.createSocialActivitySetting(activitySettingId);
	}

	/**
	* Deletes the social activity setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param activitySettingId the primary key of the social activity setting
	* @return the social activity setting that was removed
	* @throws PortalException if a social activity setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivitySetting deleteSocialActivitySetting(
		long activitySettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.deleteSocialActivitySetting(activitySettingId);
	}

	/**
	* Deletes the social activity setting from the database. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySetting the social activity setting
	* @return the social activity setting that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivitySetting deleteSocialActivitySetting(
		com.liferay.portlet.social.model.SocialActivitySetting socialActivitySetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.deleteSocialActivitySetting(socialActivitySetting);
	}

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _socialActivitySettingLocalService.dynamicQuery();
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
		return _socialActivitySettingLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _socialActivitySettingLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _socialActivitySettingLocalService.dynamicQuery(dynamicQuery,
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
		return _socialActivitySettingLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.social.model.SocialActivitySetting fetchSocialActivitySetting(
		long activitySettingId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.fetchSocialActivitySetting(activitySettingId);
	}

	/**
	* Returns the social activity setting with the primary key.
	*
	* @param activitySettingId the primary key of the social activity setting
	* @return the social activity setting
	* @throws PortalException if a social activity setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivitySetting getSocialActivitySetting(
		long activitySettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.getSocialActivitySetting(activitySettingId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the social activity settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.social.model.impl.SocialActivitySettingModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of social activity settings
	* @param end the upper bound of the range of social activity settings (not inclusive)
	* @return the range of social activity settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySetting> getSocialActivitySettings(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.getSocialActivitySettings(start,
			end);
	}

	/**
	* Returns the number of social activity settings.
	*
	* @return the number of social activity settings
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialActivitySettingsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.getSocialActivitySettingsCount();
	}

	/**
	* Updates the social activity setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialActivitySetting the social activity setting
	* @return the social activity setting that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialActivitySetting updateSocialActivitySetting(
		com.liferay.portlet.social.model.SocialActivitySetting socialActivitySetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.updateSocialActivitySetting(socialActivitySetting);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialActivitySettingLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialActivitySettingLocalService.setBeanIdentifier(beanIdentifier);
	}

	public void deleteActivitySetting(long groupId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivitySettingLocalService.deleteActivitySetting(groupId,
			className, classPK);
	}

	public void deleteActivitySettings(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialActivitySettingLocalService.deleteActivitySettings(groupId);
	}

	public com.liferay.portlet.social.model.SocialActivityDefinition getActivityDefinition(
		long groupId, java.lang.String className, int activityType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.getActivityDefinition(groupId,
			className, activityType);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivityDefinition> getActivityDefinitions(
		long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.getActivityDefinitions(groupId,
			className);
	}

	public java.util.List<com.liferay.portlet.social.model.SocialActivitySetting> getActivitySettings(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.getActivitySettings(groupId);
	}

	public boolean isEnabled(long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.isEnabled(groupId, classNameId);
	}

	public boolean isEnabled(long groupId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialActivitySettingLocalService.isEnabled(groupId,
			classNameId, classPK);
	}

	public void updateActivitySetting(long groupId, java.lang.String className,
		boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivitySettingLocalService.updateActivitySetting(groupId,
			className, enabled);
	}

	public void updateActivitySetting(long groupId, java.lang.String className,
		int activityType,
		com.liferay.portlet.social.model.SocialActivityCounterDefinition activityCounterDefinition)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivitySettingLocalService.updateActivitySetting(groupId,
			className, activityType, activityCounterDefinition);
	}

	public void updateActivitySetting(long groupId, java.lang.String className,
		long classPK, boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivitySettingLocalService.updateActivitySetting(groupId,
			className, classPK, enabled);
	}

	public void updateActivitySettings(long groupId,
		java.lang.String className, int activityType,
		java.util.List<com.liferay.portlet.social.model.SocialActivityCounterDefinition> activityCounterDefinitions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialActivitySettingLocalService.updateActivitySettings(groupId,
			className, activityType, activityCounterDefinitions);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public SocialActivitySettingLocalService getWrappedSocialActivitySettingLocalService() {
		return _socialActivitySettingLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedSocialActivitySettingLocalService(
		SocialActivitySettingLocalService socialActivitySettingLocalService) {
		_socialActivitySettingLocalService = socialActivitySettingLocalService;
	}

	public SocialActivitySettingLocalService getWrappedService() {
		return _socialActivitySettingLocalService;
	}

	public void setWrappedService(
		SocialActivitySettingLocalService socialActivitySettingLocalService) {
		_socialActivitySettingLocalService = socialActivitySettingLocalService;
	}

	private SocialActivitySettingLocalService _socialActivitySettingLocalService;
}
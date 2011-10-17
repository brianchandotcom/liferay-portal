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

import com.liferay.portal.service.ServiceWrapper;

/**
 * <p>
 * This class is a wrapper for {@link SocialEquityGroupSettingLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityGroupSettingLocalService
 * @generated
 */
public class SocialEquityGroupSettingLocalServiceWrapper
	implements SocialEquityGroupSettingLocalService,
		ServiceWrapper<SocialEquityGroupSettingLocalService> {
	public SocialEquityGroupSettingLocalServiceWrapper(
		SocialEquityGroupSettingLocalService socialEquityGroupSettingLocalService) {
		_socialEquityGroupSettingLocalService = socialEquityGroupSettingLocalService;
	}

	/**
	* Adds the social equity group setting to the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityGroupSetting the social equity group setting
	* @return the social equity group setting that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting addSocialEquityGroupSetting(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityGroupSettingLocalService.addSocialEquityGroupSetting(socialEquityGroupSetting);
	}

	/**
	* Creates a new social equity group setting with the primary key. Does not add the social equity group setting to the database.
	*
	* @param equityGroupSettingId the primary key for the new social equity group setting
	* @return the new social equity group setting
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting createSocialEquityGroupSetting(
		long equityGroupSettingId) {
		return _socialEquityGroupSettingLocalService.createSocialEquityGroupSetting(equityGroupSettingId);
	}

	/**
	* Deletes the social equity group setting with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param equityGroupSettingId the primary key of the social equity group setting
	* @throws PortalException if a social equity group setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialEquityGroupSetting(long equityGroupSettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityGroupSettingLocalService.deleteSocialEquityGroupSetting(equityGroupSettingId);
	}

	/**
	* Deletes the social equity group setting from the database. Also notifies the appropriate model listeners.
	*
	* @param socialEquityGroupSetting the social equity group setting
	* @throws SystemException if a system exception occurred
	*/
	public void deleteSocialEquityGroupSetting(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		_socialEquityGroupSettingLocalService.deleteSocialEquityGroupSetting(socialEquityGroupSetting);
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
		return _socialEquityGroupSettingLocalService.dynamicQuery(dynamicQuery);
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
		return _socialEquityGroupSettingLocalService.dynamicQuery(dynamicQuery,
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
		return _socialEquityGroupSettingLocalService.dynamicQuery(dynamicQuery,
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
		return _socialEquityGroupSettingLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the social equity group setting with the primary key.
	*
	* @param equityGroupSettingId the primary key of the social equity group setting
	* @return the social equity group setting
	* @throws PortalException if a social equity group setting with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting getSocialEquityGroupSetting(
		long equityGroupSettingId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityGroupSettingLocalService.getSocialEquityGroupSetting(equityGroupSettingId);
	}

	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityGroupSettingLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the social equity group settings.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of social equity group settings
	* @param end the upper bound of the range of social equity group settings (not inclusive)
	* @return the range of social equity group settings
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.social.model.SocialEquityGroupSetting> getSocialEquityGroupSettings(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityGroupSettingLocalService.getSocialEquityGroupSettings(start,
			end);
	}

	/**
	* Returns the number of social equity group settings.
	*
	* @return the number of social equity group settings
	* @throws SystemException if a system exception occurred
	*/
	public int getSocialEquityGroupSettingsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityGroupSettingLocalService.getSocialEquityGroupSettingsCount();
	}

	/**
	* Updates the social equity group setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityGroupSetting the social equity group setting
	* @return the social equity group setting that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting updateSocialEquityGroupSetting(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityGroupSettingLocalService.updateSocialEquityGroupSetting(socialEquityGroupSetting);
	}

	/**
	* Updates the social equity group setting in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param socialEquityGroupSetting the social equity group setting
	* @param merge whether to merge the social equity group setting with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the social equity group setting that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.social.model.SocialEquityGroupSetting updateSocialEquityGroupSetting(
		com.liferay.portlet.social.model.SocialEquityGroupSetting socialEquityGroupSetting,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityGroupSettingLocalService.updateSocialEquityGroupSetting(socialEquityGroupSetting,
			merge);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _socialEquityGroupSettingLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_socialEquityGroupSettingLocalService.setBeanIdentifier(beanIdentifier);
	}

	/**
	* Returns <code>true</code> if social equity is turned on for the model
	* (asset type) in the group.
	*
	* @param groupId the primary key of the group
	* @param className the class name for the target asset type
	* @return <code>true</code> if social equity is enabled for the model;
	<code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean isEnabled(long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityGroupSettingLocalService.isEnabled(groupId,
			className);
	}

	/**
	* Returns <code>true</code> if the specified social equity scoring type is
	* turned on for the model (asset type) in the group.
	*
	* @param groupId the primary key of the group
	* @param className the class name for the target asset type
	* @param type the social equity score type, acceptable values are {@link
	SocialEquitySettingConstants.TYPE_INFORMATION} and {@link
	SocialEquitySettingConstants.TYPE_PARTICIPATION}
	* @return <code>true</code> if the given type of social equity scoring is
	enabled for the model; <code>false</code> otherwise
	* @throws SystemException if a system exception occurred
	*/
	public boolean isEnabled(long groupId, java.lang.String className, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _socialEquityGroupSettingLocalService.isEnabled(groupId,
			className, type);
	}

	/**
	* Updates the group related social equity settings for the group and model
	* (asset type).
	*
	* <p>
	* This method stores whether the social equity scoring type (information
	* or participation) is turned on for the model in the group.
	* </p>
	*
	* @param groupId the primary key of the group
	* @param className the class name for the target asset
	* @param type the social equity score type, acceptable values are {@link
	SocialEquitySettingConstants.TYPE_INFORMATION}, {@link
	SocialEquitySettingConstants.TYPE_PARTICIPATION}
	* @param enabled whether social equity is turned on
	* @throws PortalException if the group could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void updateEquityGroupSetting(long groupId,
		java.lang.String className, int type, boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_socialEquityGroupSettingLocalService.updateEquityGroupSetting(groupId,
			className, type, enabled);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public SocialEquityGroupSettingLocalService getWrappedSocialEquityGroupSettingLocalService() {
		return _socialEquityGroupSettingLocalService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedSocialEquityGroupSettingLocalService(
		SocialEquityGroupSettingLocalService socialEquityGroupSettingLocalService) {
		_socialEquityGroupSettingLocalService = socialEquityGroupSettingLocalService;
	}

	public SocialEquityGroupSettingLocalService getWrappedService() {
		return _socialEquityGroupSettingLocalService;
	}

	public void setWrappedService(
		SocialEquityGroupSettingLocalService socialEquityGroupSettingLocalService) {
		_socialEquityGroupSettingLocalService = socialEquityGroupSettingLocalService;
	}

	private SocialEquityGroupSettingLocalService _socialEquityGroupSettingLocalService;
}
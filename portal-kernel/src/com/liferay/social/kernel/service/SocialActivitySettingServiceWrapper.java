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
 * Provides a wrapper for {@link SocialActivitySettingService}.
 *
 * @author Brian Wing Shun Chan
 * @see SocialActivitySettingService
 * @generated
 */
public class SocialActivitySettingServiceWrapper
	implements ServiceWrapper<SocialActivitySettingService>,
			   SocialActivitySettingService {

	public SocialActivitySettingServiceWrapper(
		SocialActivitySettingService socialActivitySettingService) {

		_socialActivitySettingService = socialActivitySettingService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SocialActivitySettingServiceUtil} to access the social activity setting remote service. Add custom service methods to <code>com.liferay.portlet.social.service.impl.SocialActivitySettingServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.social.kernel.model.SocialActivityDefinition
			getActivityDefinition(
				long groupId, String className, int activityType)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivitySettingService.getActivityDefinition(
			groupId, className, activityType);
	}

	@Override
	public java.util.List
		<com.liferay.social.kernel.model.SocialActivityDefinition>
				getActivityDefinitions(long groupId, String className)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivitySettingService.getActivityDefinitions(
			groupId, className);
	}

	@Override
	public java.util.List<com.liferay.social.kernel.model.SocialActivitySetting>
			getActivitySettings(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivitySettingService.getActivitySettings(groupId);
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getJSONActivityDefinitions(
			long groupId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _socialActivitySettingService.getJSONActivityDefinitions(
			groupId, className);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _socialActivitySettingService.getOSGiServiceIdentifier();
	}

	@Override
	public void updateActivitySetting(
			long groupId, String className, boolean enabled)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivitySettingService.updateActivitySetting(
			groupId, className, enabled);
	}

	@Override
	public void updateActivitySetting(
			long groupId, String className, int activityType,
			com.liferay.social.kernel.model.SocialActivityCounterDefinition
				activityCounterDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivitySettingService.updateActivitySetting(
			groupId, className, activityType, activityCounterDefinition);
	}

	@Override
	public void updateActivitySettings(
			long groupId, String className, int activityType,
			java.util.List
				<com.liferay.social.kernel.model.
					SocialActivityCounterDefinition> activityCounterDefinitions)
		throws com.liferay.portal.kernel.exception.PortalException {

		_socialActivitySettingService.updateActivitySettings(
			groupId, className, activityType, activityCounterDefinitions);
	}

	@Override
	public SocialActivitySettingService getWrappedService() {
		return _socialActivitySettingService;
	}

	@Override
	public void setWrappedService(
		SocialActivitySettingService socialActivitySettingService) {

		_socialActivitySettingService = socialActivitySettingService;
	}

	private SocialActivitySettingService _socialActivitySettingService;

}
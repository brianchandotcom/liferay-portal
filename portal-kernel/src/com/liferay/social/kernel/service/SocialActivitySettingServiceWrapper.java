/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.social.kernel.model.SocialActivitySetting;

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

	public SocialActivitySettingServiceWrapper() {
		this(null);
	}

	public SocialActivitySettingServiceWrapper(
		SocialActivitySettingService socialActivitySettingService) {

		_socialActivitySettingService = socialActivitySettingService;
	}

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
	public java.util.List<SocialActivitySetting> getActivitySettings(
			long groupId)
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
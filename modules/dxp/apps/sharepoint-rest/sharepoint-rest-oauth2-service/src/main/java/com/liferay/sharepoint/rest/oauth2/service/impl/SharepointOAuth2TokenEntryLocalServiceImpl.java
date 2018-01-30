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

package com.liferay.sharepoint.oauth2.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.sharepoint.oauth2.model.SharepointOAuth2TokenEntry;
import com.liferay.sharepoint.oauth2.service.base.SharepointOAuth2TokenEntryLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SharepointOAuth2TokenEntryLocalServiceImpl
	extends SharepointOAuth2TokenEntryLocalServiceBaseImpl {

	@Override
	public SharepointOAuth2TokenEntry addSharepointOAuth2TokenEntry(
			long userId, String configurationPid, String accessToken,
			String refreshToken, Date expirationDate)
		throws PortalException {

		SharepointOAuth2TokenEntry sharepointOAuth2TokenEntry =
			sharepointOAuth2TokenEntryPersistence.fetchByU_C(
				userId, configurationPid);

		if (sharepointOAuth2TokenEntry != null) {
			sharepointOAuth2TokenEntry.setAccessToken(accessToken);
			sharepointOAuth2TokenEntry.setExpirationDate(expirationDate);
			sharepointOAuth2TokenEntry.setRefreshToken(refreshToken);

			sharepointOAuth2TokenEntryPersistence.update(
				sharepointOAuth2TokenEntry);

			return sharepointOAuth2TokenEntry;
		}

		long sharepointOAuth2TokenEntryId = counterLocalService.increment();

		sharepointOAuth2TokenEntry =
			sharepointOAuth2TokenEntryPersistence.create(
				sharepointOAuth2TokenEntryId);

		sharepointOAuth2TokenEntry.setUserId(userId);

		User user = userLocalService.getUser(userId);

		sharepointOAuth2TokenEntry.setUserName(user.getFullName());

		Date now = new Date();

		sharepointOAuth2TokenEntry.setCreateDate(now);

		sharepointOAuth2TokenEntry.setAccessToken(accessToken);
		sharepointOAuth2TokenEntry.setConfigurationPid(configurationPid);
		sharepointOAuth2TokenEntry.setExpirationDate(expirationDate);
		sharepointOAuth2TokenEntry.setRefreshToken(refreshToken);

		return sharepointOAuth2TokenEntryPersistence.update(
			sharepointOAuth2TokenEntry);
	}

	@Override
	public void deleteSharepointOAuth2TokenEntry(
			long userId, String configurationPid)
		throws PortalException {

		sharepointOAuth2TokenEntryPersistence.removeByU_C(
			userId, configurationPid);
	}

	@Override
	public SharepointOAuth2TokenEntry fetchSharepointOAuth2TokenEntry(
		long userId, String configurationPid) {

		return sharepointOAuth2TokenEntryPersistence.fetchByU_C(
			userId, configurationPid);
	}

	@Override
	public SharepointOAuth2TokenEntry getSharepointOAuth2TokenEntry(
			long userId, String configurationPid)
		throws PortalException {

		return sharepointOAuth2TokenEntryPersistence.findByU_C(
			userId, configurationPid);
	}

}
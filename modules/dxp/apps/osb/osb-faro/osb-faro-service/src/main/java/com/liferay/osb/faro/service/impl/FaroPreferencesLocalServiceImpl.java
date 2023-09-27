/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.service.impl;

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.service.base.FaroPreferencesLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Kong
 */
@Component(
	property = "model.class.name=com.liferay.osb.faro.model.FaroPreferences",
	service = AopService.class
)
public class FaroPreferencesLocalServiceImpl
	extends FaroPreferencesLocalServiceBaseImpl {

	public FaroPreferences deleteFaroPreferences(long groupId, long ownerId) {
		FaroPreferences faroPreferences = faroPreferencesPersistence.fetchByG_O(
			groupId, ownerId);

		if (faroPreferences != null) {
			faroPreferencesPersistence.remove(faroPreferences);
		}

		return faroPreferences;
	}

	public void deleteFaroPreferencesByGroupId(long groupId) {
		faroPreferencesPersistence.removeByGroupId(groupId);
	}

	public FaroPreferences fetchFaroPreferences(long groupId, long ownerId) {
		return faroPreferencesPersistence.fetchByG_O(groupId, ownerId);
	}

	public List<FaroPreferences> getFaroPreferencesByGroupId(long groupId) {
		return faroPreferencesPersistence.findByGroupId(groupId);
	}

	public FaroPreferences savePreferences(
			long userId, long groupId, long ownerId, String preferences)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		FaroPreferences faroPreferences = faroPreferencesPersistence.fetchByG_O(
			groupId, ownerId);

		long now = System.currentTimeMillis();

		if (faroPreferences == null) {
			faroPreferences = faroPreferencesPersistence.create(
				counterLocalService.increment());

			faroPreferences.setGroupId(groupId);
			faroPreferences.setCreateTime(now);
			faroPreferences.setOwnerId(ownerId);
		}

		faroPreferences.setUserId(userId);
		faroPreferences.setUserName(user.getFullName());
		faroPreferences.setModifiedTime(now);
		faroPreferences.setPreferences(preferences);

		return updateFaroPreferences(faroPreferences);
	}

	@Reference
	private UserLocalService _userLocalService;

}
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.service.impl;

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.service.base.FaroPreferencesLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

/**
 * @author Matthew Kong
 */
public class FaroPreferencesLocalServiceImpl
	extends FaroPreferencesLocalServiceBaseImpl {

	@Override
	public FaroPreferences deleteFaroPreferences(long groupId, long ownerId) {
		FaroPreferences faroPreferences = faroPreferencesPersistence.fetchByG_O(
			groupId, ownerId);

		if (faroPreferences != null) {
			faroPreferencesPersistence.remove(faroPreferences);
		}

		return faroPreferences;
	}

	@Override
	public void deleteFaroPreferencesByGroupId(long groupId) {
		faroPreferencesPersistence.removeByGroupId(groupId);
	}

	@Override
	public FaroPreferences fetchFaroPreferences(long groupId, long ownerId) {
		return faroPreferencesPersistence.fetchByG_O(groupId, ownerId);
	}

	@Override
	public FaroPreferences savePreferences(
			long userId, long groupId, long ownerId, String preferences)
		throws PortalException {

		User user = userLocalService.getUser(userId);

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

}
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

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.model.FaroPreferences;
import com.liferay.osb.faro.web.internal.model.preferences.WorkspacePreferences;
import com.liferay.osb.faro.web.internal.util.JSONUtil;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class FaroPreferencesDisplay {

	public FaroPreferencesDisplay() {
	}

	public FaroPreferencesDisplay(FaroPreferences faroPreferences)
		throws Exception {

		this(
			faroPreferences,
			JSONUtil.readValue(
				faroPreferences.getPreferences(), WorkspacePreferences.class));
	}

	public FaroPreferencesDisplay(
		FaroPreferences faroPreferences,
		WorkspacePreferences workspacePreferences) {

		_groupId = faroPreferences.getGroupId();
		_ownerId = faroPreferences.getOwnerId();
		_preferences = workspacePreferences;
	}

	public FaroPreferencesDisplay(long groupId, long ownerId) {
		_groupId = groupId;
		_ownerId = ownerId;

		_preferences = new WorkspacePreferences();
	}

	private long _groupId;
	private long _ownerId;
	private WorkspacePreferences _preferences;

}
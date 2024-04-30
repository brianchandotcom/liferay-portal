/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.entry;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryContext {

	public ObjectEntryContext(
		long groupId, long objectDefinitionId, long userId,
		Map<String, Serializable> values) {

		_groupId = groupId;
		_objectDefinitionId = objectDefinitionId;
		_userId = userId;
		_values = values;
	}

	public long getGroupId() {
		return _groupId;
	}

	public long getObjectDefinitionId() {
		return _objectDefinitionId;
	}

	public long getUserId() {
		return _userId;
	}

	public Map<String, Serializable> getValues() {
		return _values;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setObjectDefinitionId(long objectDefinitionId) {
		_objectDefinitionId = objectDefinitionId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public void setValues(Map<String, Serializable> values) {
		_values = values;
	}

	private long _groupId;
	private long _objectDefinitionId;
	private long _userId;
	private Map<String, Serializable> _values;

}
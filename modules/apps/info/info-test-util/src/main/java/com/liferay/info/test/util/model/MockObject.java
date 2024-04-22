/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.test.util.model;

import com.liferay.info.field.InfoField;
import com.liferay.portal.kernel.security.permission.ActionKeys;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class MockObject {

	public MockObject(long classPK) {
		this(classPK, true, true);
	}

	public MockObject(
		long classPK, boolean updatePermission, boolean viewPermission) {

		_classPK = classPK;
		_updatePermission = updatePermission;
		_viewPermission = viewPermission;
	}

	public void addInfoField(InfoField infoField, Object value) {
		_infoFieldsMap.put(infoField, value);
	}

	public long getClassPK() {
		return _classPK;
	}

	public Map<InfoField<?>, Object> getInfoFieldsMap() {
		return _infoFieldsMap;
	}

	public boolean hasPermission(String actionId) {
		if (Objects.equals(ActionKeys.VIEW, actionId)) {
			return _viewPermission;
		}

		if (Objects.equals(ActionKeys.UPDATE, actionId)) {
			return _updatePermission;
		}

		return false;
	}

	private final long _classPK;
	private final Map<InfoField<?>, Object> _infoFieldsMap = new HashMap<>();
	private final boolean _updatePermission;
	private final boolean _viewPermission;

}
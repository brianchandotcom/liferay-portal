/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectFilter;
import com.liferay.object.model.ObjectStateFlow;

import java.util.List;
import java.util.Objects;

/**
 * @author Marco Leo
 */
public class ObjectFieldSettingImpl extends ObjectFieldSettingBaseImpl {

	@Override
	public boolean compareName(String name) {
		if (Objects.equals(getName(), name)) {
			return true;
		}

		return false;
	}

	public List<ObjectFilter> getObjectFilters() {
		return _objectFilters;
	}

	public ObjectStateFlow getObjectStateFlow() {
		return _objectStateFlow;
	}

	public void setObjectFilters(List<ObjectFilter> objectFilters) {
		_objectFilters = objectFilters;
	}

	public void setObjectStateFlow(ObjectStateFlow objectStateFlow) {
		_objectStateFlow = objectStateFlow;
	}

	private List<ObjectFilter> _objectFilters;
	private ObjectStateFlow _objectStateFlow;

}
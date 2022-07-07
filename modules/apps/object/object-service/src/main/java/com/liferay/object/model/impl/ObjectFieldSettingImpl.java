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

package com.liferay.object.model.impl;

import com.liferay.object.model.ObjectFilter;
import com.liferay.object.model.ObjectStateFlow;

import java.util.List;

/**
 * @author Marco Leo
 */
public class ObjectFieldSettingImpl extends ObjectFieldSettingBaseImpl {

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
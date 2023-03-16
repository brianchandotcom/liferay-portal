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

package com.liferay.object.field.setting.builder;

import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldSettingLocalServiceUtil;

/**
 * @author Murilo Stodolni
 */
public class ObjectFieldSettingBuilder {

	public ObjectFieldSetting build() {
		return _objectFieldSetting;
	}

	public ObjectFieldSettingBuilder name(String name) {
		_objectFieldSetting.setName(name);

		return this;
	}

	public ObjectFieldSettingBuilder value(String value) {
		_objectFieldSetting.setValue(value);

		return this;
	}

	private final ObjectFieldSetting _objectFieldSetting =
		ObjectFieldSettingLocalServiceUtil.createObjectFieldSetting(0L);

}
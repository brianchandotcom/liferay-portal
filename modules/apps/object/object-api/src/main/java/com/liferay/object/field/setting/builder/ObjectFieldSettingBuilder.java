/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
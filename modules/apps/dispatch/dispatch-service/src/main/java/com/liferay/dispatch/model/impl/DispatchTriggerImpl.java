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

package com.liferay.dispatch.model.impl;

import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Alessio Antonio Rendina
 */
public class DispatchTriggerImpl extends DispatchTriggerBaseImpl {

	public DispatchTriggerImpl() {
	}

	public UnicodeProperties getTaskSettingsUnicodeProperties() {
		if (_taskSettingsUnicodeProperties == null) {
			_taskSettingsUnicodeProperties = new UnicodeProperties(true);

			_taskSettingsUnicodeProperties.fastLoad(getTaskSettings());
		}

		return _taskSettingsUnicodeProperties;
	}

	public void setTaskSettings(String taskSettings) {
		super.setTaskSettings(taskSettings);

		_taskSettingsUnicodeProperties = null;
	}

	public void setTaskSettingsUnicodeProperties(
		UnicodeProperties taskSettingsUnicodeProperties) {

		_taskSettingsUnicodeProperties = taskSettingsUnicodeProperties;

		if (_taskSettingsUnicodeProperties == null) {
			_taskSettingsUnicodeProperties = new UnicodeProperties();
		}

		super.setTaskSettings(_taskSettingsUnicodeProperties.toString());
	}

	private transient UnicodeProperties _taskSettingsUnicodeProperties;

}
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

import com.liferay.dispatch.trigger.DispatchTriggerExecutionMode;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Alessio Antonio Rendina
 */
public class DispatchTriggerImpl extends DispatchTriggerBaseImpl {

	public DispatchTriggerImpl() {
	}

	public DispatchTriggerExecutionMode getDispatchTriggerExecutionMode() {
		if (_dispatchTriggerExecutionMode == null) {
			_dispatchTriggerExecutionMode =
				DispatchTriggerExecutionMode.ALL_NODES;

			if (isSingleNodeExecution()) {
				_dispatchTriggerExecutionMode =
					DispatchTriggerExecutionMode.SINGLE_NODE;
			}
		}

		return _dispatchTriggerExecutionMode;
	}

	public UnicodeProperties getTaskSettingsUnicodeProperties() {
		if (_taskSettingsUnicodeProperties == null) {
			_taskSettingsUnicodeProperties = new UnicodeProperties(true);

			_taskSettingsUnicodeProperties.fastLoad(getTaskSettings());
		}

		return _taskSettingsUnicodeProperties;
	}

	public void setDispatchTriggerExecutionMode(
		DispatchTriggerExecutionMode dispatchTriggerExecutionMode) {

		_dispatchTriggerExecutionMode = dispatchTriggerExecutionMode;

		boolean singleNodeExecution = false;

		if (_dispatchTriggerExecutionMode ==
				DispatchTriggerExecutionMode.SINGLE_NODE) {

			singleNodeExecution = true;
		}

		super.setSingleNodeExecution(singleNodeExecution);
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

	private transient DispatchTriggerExecutionMode
		_dispatchTriggerExecutionMode;
	private transient UnicodeProperties _taskSettingsUnicodeProperties;

}
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

	public UnicodeProperties getTaskUnicodeProperties() {
		if (_taskUnicodeProperties == null) {
			_taskUnicodeProperties = new UnicodeProperties(true);

			_taskUnicodeProperties.fastLoad(getTaskProperties());
		}

		return _taskUnicodeProperties;
	}

	public void setTaskProperties(String taskProperties) {
		super.setTaskProperties(taskProperties);

		_taskUnicodeProperties = null;
	}

	public void setTaskUnicodeProperties(
		UnicodeProperties taskUnicodeProperties) {

		_taskUnicodeProperties = taskUnicodeProperties;

		if (_taskUnicodeProperties == null) {
			_taskUnicodeProperties = new UnicodeProperties();
		}

		super.setTaskProperties(_taskUnicodeProperties.toString());
	}

	private transient UnicodeProperties _taskUnicodeProperties;

}
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
 * @author Igor Beslic
 */
public class DispatchTriggerImpl extends DispatchTriggerBaseImpl {

	public DispatchTriggerImpl() {
	}

	@Override
	public UnicodeProperties getDispatchTaskSettingsUnicodeProperties() {
		if (_dispatchTaskSettingsUnicodeProperties == null) {
			_dispatchTaskSettingsUnicodeProperties = new UnicodeProperties(
				true);

			_dispatchTaskSettingsUnicodeProperties.fastLoad(
				getDispatchTaskSettings());
		}

		return _dispatchTaskSettingsUnicodeProperties;
	}

	/**
	 * @return
	 *
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 *             #getDispatchTaskSettingsUnicodeProperties()}
	 */
	@Deprecated
	@Override
	public UnicodeProperties getTaskSettingsUnicodeProperties() {
		return getDispatchTaskSettingsUnicodeProperties();
	}

	@Override
	public void setDispatchTaskSettings(String dispatchTaskSettings) {
		super.setDispatchTaskSettings(dispatchTaskSettings);

		_dispatchTaskSettingsUnicodeProperties = null;
	}

	@Override
	public void setDispatchTaskSettingsUnicodeProperties(
		UnicodeProperties dispatchTaskSettingsUnicodeProperties) {

		_dispatchTaskSettingsUnicodeProperties =
			dispatchTaskSettingsUnicodeProperties;

		if (_dispatchTaskSettingsUnicodeProperties == null) {
			_dispatchTaskSettingsUnicodeProperties = new UnicodeProperties();
		}

		super.setDispatchTaskSettings(
			_dispatchTaskSettingsUnicodeProperties.toString());
	}

	/**
	 * @param      taskSettings
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 *             #setDispatchTaskSettings(String)}
	 */
	@Deprecated
	@Override
	public void setTaskSettings(String taskSettings) {
		setDispatchTaskSettings(taskSettings);
	}

	/**
	 * @param      taskSettingsUnicodeProperties
	 * @deprecated As of Cavanaugh (7.4.x), use {@link
	 *             #setDispatchTaskSettingsUnicodeProperties(UnicodeProperties)}
	 */
	@Deprecated
	@Override
	public void setTaskSettingsUnicodeProperties(
		UnicodeProperties taskSettingsUnicodeProperties) {

		setDispatchTaskSettingsUnicodeProperties(taskSettingsUnicodeProperties);
	}

	private transient UnicodeProperties _dispatchTaskSettingsUnicodeProperties;

}
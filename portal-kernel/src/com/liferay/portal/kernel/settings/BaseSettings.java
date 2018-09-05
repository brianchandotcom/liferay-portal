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

package com.liferay.portal.kernel.settings;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 * @author Iván Zaera
 */
public abstract class BaseSettings implements Settings {

	public BaseSettings() {
	}

	public BaseSettings(Settings parentSettings) {
		this.parentSettings = parentSettings;
	}

	@Override
	public ModifiableSettings getModifiableSettings() {
		if (this instanceof ModifiableSettings) {
			return (ModifiableSettings)this;
		}
		else if (parentSettings == null) {
			return null;
		}

		return parentSettings.getModifiableSettings();
	}

	@Override
	public Settings getParentSettings() {
		return parentSettings;
	}

	@Override
	public String getValue(String key, String defaultValue) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		String value = doGetValue(key);

		if ((value == null) && (parentSettings != null)) {
			value = parentSettings.getValue(key, defaultValue);
		}

		if (Validator.isNull(value)) {
			value = defaultValue;
		}

		return value;
	}

	@Override
	public String[] getValues(String key, String[] defaultValue) {
		if (key == null) {
			throw new IllegalArgumentException("Key is null");
		}

		String[] values = doGetValues(key);

		if (ArrayUtil.isEmpty(values) && (parentSettings != null)) {
			values = parentSettings.getValues(key, defaultValue);
		}

		if (ArrayUtil.isEmpty(values)) {
			values = defaultValue;
		}

		return values;
	}

	protected abstract String doGetValue(String key);

	protected abstract String[] doGetValues(String key);

	protected Settings parentSettings;

}
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

package com.liferay.dispatch.service.test;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * Dispatch trigger values holder
 *
 * @author Igor Beslic
 */
public class DispatchTriggerValues {

	/**
	 * Returns DispatchTriggerValues object with <code>active</code> and
	 * <code>cronExpression</code> updated with new random values.
	 *
	 * If <code>nameSalt</code> less then <code>0</code> {@link #getName()}
	 * method
	 * returns <code>null</code>.
	 *
	 * @param  dispatchTriggerValues the original dispatch trigger values
	 * @param  nameSalt the dispatch trigger name suffix
	 * @return updated random DispatchTriggerValues
	 */
	public static DispatchTriggerValues randomDispatchTriggerValues(
		DispatchTriggerValues dispatchTriggerValues, int nameSalt) {

		String name = null;

		if (nameSalt > -1) {
			name = String.format(
				_NAME_PATTERN, dispatchTriggerValues.getUserId(), nameSalt);
		}

		return new DispatchTriggerValues(
			RandomTestUtil.randomBoolean(),
			dispatchTriggerValues.getCompanyId(), _randomCronExpression(),
			dispatchTriggerValues.getTaskType(),
			dispatchTriggerValues.getTaskSettingsUnicodeProperties(), name,
			dispatchTriggerValues.isSystem(),
			dispatchTriggerValues.getUserId());
	}

	/**
	 * Returns DispatchTriggerValues object initialized with random values.
	 *
	 * If <code>nameSalt</code> less then <code>0</code> {@link #getName()}
	 * method
	 * returns <code>null</code>.
	 *
	 * @param  user the owner user of dispatch trigger
	 * @param  nameSalt the dispatch trigger name suffix
	 * @return random dispatch trigger
	 */
	public static DispatchTriggerValues randomDispatchTriggerValues(
		User user, int nameSalt) {

		String name = null;

		if (nameSalt > -1) {
			name = String.format(_NAME_PATTERN, user.getUserId(), nameSalt);
		}

		return new DispatchTriggerValues(
			RandomTestUtil.randomBoolean(), user.getCompanyId(),
			_randomCronExpression(), RandomTestUtil.randomString(20),
			RandomTestUtil.randomUnicodeProperties(
				RandomTestUtil.randomInt(10, 30), 32, 64),
			name, RandomTestUtil.randomBoolean(), user.getUserId());
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getCronExpression() {
		return _cronExpression;
	}

	public String getName() {
		return _name;
	}

	public String getTaskSettingsProperty(String key) {
		if (_taskSettingsUnicodeProperties == null) {
			return StringPool.BLANK;
		}

		return _taskSettingsUnicodeProperties.getProperty(key);
	}

	public UnicodeProperties getTaskSettingsUnicodeProperties() {
		return _taskSettingsUnicodeProperties;
	}

	public int getTaskSettingsUnicodePropertiesSize() {
		if (_taskSettingsUnicodeProperties == null) {
			return 0;
		}

		return _taskSettingsUnicodeProperties.size();
	}

	public String getTaskType() {
		return _taskType;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isActive() {
		return _active;
	}

	public boolean isSystem() {
		return _system;
	}

	private static String _randomCronExpression() {
		return String.format(
			"0 0 0 ? %d/2 * 2077", RandomTestUtil.randomInt(1, 12));
	}

	private DispatchTriggerValues(
		boolean active, long companyId, String cronExpression, String taskType,
		UnicodeProperties unicodeProperties, String name, boolean system,
		long userId) {

		_active = active;
		_companyId = companyId;
		_cronExpression = cronExpression;
		_taskType = taskType;
		_taskSettingsUnicodeProperties = unicodeProperties;
		_name = name;
		_system = system;
		_userId = userId;
	}

	private static final String _NAME_PATTERN = "TEST-TRIGGER-%06d-%06d";

	private final boolean _active;
	private final long _companyId;
	private final String _cronExpression;
	private final String _name;
	private final boolean _system;
	private final UnicodeProperties _taskSettingsUnicodeProperties;
	private final String _taskType;
	private final long _userId;

}
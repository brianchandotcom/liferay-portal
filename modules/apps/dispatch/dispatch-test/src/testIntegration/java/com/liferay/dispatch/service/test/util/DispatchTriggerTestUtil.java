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

package com.liferay.dispatch.service.test.util;

import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.persistence.DispatchTriggerUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Objects;

/**
 * @author Igor Beslic
 */
public class DispatchTriggerTestUtil {

	public static DispatchTrigger randomDispatchTrigger(
		DispatchTrigger dispatchTrigger, int nameSalt) {

		Objects.requireNonNull(dispatchTrigger);

		return _randomDispatchTrigger(
			RandomTestUtil.randomBoolean(), dispatchTrigger.getCompanyId(),
			CronExpressionUtil.getCronExpression(), _randomTaskClusterMode(),
			dispatchTrigger.getDispatchTaskExecutorType(),
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties(),
			_randomName(nameSalt), dispatchTrigger.isSystem(),
			dispatchTrigger.getUserId());
	}

	public static DispatchTrigger randomDispatchTrigger(
		User user, String dispatchTaskExecutorType, int nameSalt) {

		Objects.requireNonNull(user);

		return _randomDispatchTrigger(
			RandomTestUtil.randomBoolean(), user.getCompanyId(),
			CronExpressionUtil.getCronExpression(), 0, dispatchTaskExecutorType,
			RandomTestUtil.randomUnicodeProperties(
				RandomTestUtil.randomInt(10, 30), 32, 64),
			_randomName(nameSalt), RandomTestUtil.randomBoolean(),
			user.getUserId());
	}

	private static DispatchTrigger _randomDispatchTrigger(
		boolean active, long companyId, String cronExpression,
		int dispatchTaskClusterMode, String dispatchTaskExecutorType,
		UnicodeProperties unicodeProperties, String name, boolean system,
		long userId) {

		DispatchTrigger dispatchTrigger = DispatchTriggerUtil.create(
			RandomTestUtil.nextLong());

		dispatchTrigger.setCompanyId(companyId);
		dispatchTrigger.setUserId(userId);
		dispatchTrigger.setActive(active);
		dispatchTrigger.setCronExpression(cronExpression);
		dispatchTrigger.setDispatchTaskClusterMode(dispatchTaskClusterMode);
		dispatchTrigger.setDispatchTaskExecutorType(dispatchTaskExecutorType);
		dispatchTrigger.setDispatchTaskSettingsUnicodeProperties(
			unicodeProperties);
		dispatchTrigger.setName(name);
		dispatchTrigger.setSystem(system);

		return dispatchTrigger;
	}

	private static String _randomName(int nameSalt) {
		if (nameSalt < 0) {
			return null;
		}

		return String.format("TEST-TRIGGER-%06d", nameSalt);
	}

	private static int _randomTaskClusterMode() {
		DispatchTaskClusterMode[] dispatchTaskClusterModes =
			DispatchTaskClusterMode.values();

		DispatchTaskClusterMode dispatchTaskClusterMode =
			dispatchTaskClusterModes
				[RandomTestUtil.randomInt(
					0, dispatchTaskClusterModes.length - 1)];

		return dispatchTaskClusterMode.getMode();
	}

}
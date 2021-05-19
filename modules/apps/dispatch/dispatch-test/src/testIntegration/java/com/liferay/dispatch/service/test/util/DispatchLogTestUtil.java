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

import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.service.persistence.DispatchLogPersistence;
import com.liferay.dispatch.service.persistence.DispatchLogUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Date;

/**
 * @author Igor Beslic
 */
public class DispatchLogTestUtil {

	public static DispatchLog randomDispatchLog(
		User user, DispatchTaskStatus dispatchTaskStatus) {

		final Date startDate = RandomTestUtil.nextDate();

		return _randomDispatchLog(
			user.getCompanyId(),
			new Date(
				startDate.getTime() + RandomTestUtil.randomInt(60000, 120000)),
			RandomTestUtil.randomString(2000),
			RandomTestUtil.randomString(3000), startDate,
			dispatchTaskStatus.getStatus(), user.getUserId());
	}

	private static DispatchLog _randomDispatchLog(
		long companyId, Date endDate, String error, String output,
		Date startDate, int status, long userId) {

		DispatchLogPersistence dispatchLogPersistence =
			DispatchLogUtil.getPersistence();

		DispatchLog dispatchLog = dispatchLogPersistence.create(
			RandomTestUtil.nextLong());

		dispatchLog.setCompanyId(companyId);
		dispatchLog.setUserId(userId);
		dispatchLog.setEndDate(endDate);
		dispatchLog.setError(error);
		dispatchLog.setOutput(output);
		dispatchLog.setStartDate(startDate);
		dispatchLog.setStatus(status);

		return dispatchLog;
	}

}
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

package com.liferay.dispatch.executor.sample.internal;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	property = {
		"dispatch.task.executor.name=" + SampleDispatchTaskExecutor.KEY,
		"dispatch.task.executor.type=Sample"
	},
	service = DispatchTaskExecutor.class
)
public class SampleDispatchTaskExecutor extends BaseDispatchTaskExecutor {

	public static final String KEY = "dispatch-executor-sample-name";

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws Exception {

		UnicodeProperties dispatchTaskSettingsUnicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		long time = GetterUtil.getLong(
			dispatchTaskSettingsUnicodeProperties.getProperty("sleepTime"),
			5000);

		try {
			Thread.sleep(time);

			dispatchTaskExecutorOutput.setOutput(
				StringBundler.concat(
					"Slept for ", time, " milliseconds and woke up on ",
					new Date()));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			dispatchTaskExecutorOutput.setError(
				StringBundler.concat(
					"Unable to sleep for ", time, " milliseconds"));
		}
	}

	@Override
	public String getName() {
		return KEY;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SampleDispatchTaskExecutor.class);

}
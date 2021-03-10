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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Beslic
 */
@Component(
	property = {
		"dispatch.task.executor.name=dispatch-executor-sample-name",
		"dispatch.task.executor.type=Sample"
	},
	service = DispatchTaskExecutor.class
)
public class SampleDispatchTaskExecutor extends BaseDispatchTaskExecutor {

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws IOException, PortalException {

		UnicodeProperties dispatchTaskSettingsUnicodeProperties =
			dispatchTrigger.getDispatchTaskSettingsUnicodeProperties();

		long taskInMils = GetterUtil.getLong(
			dispatchTaskSettingsUnicodeProperties.getProperty("sleepTime"),
			_SLEEP_TIME_MILS);

		try {
			Thread.sleep(taskInMils);

			dispatchTaskExecutorOutput.setOutput(
				StringBundler.concat(
					"Slept for ", taskInMils, " milli seconds and woke up in ",
					new Date()));
		}
		catch (Exception exception) {
			dispatchTaskExecutorOutput.setError(
				StringBundler.concat(
					"Unable to sleep for ", taskInMils, " milli seconds"));
		}
	}

	@Override
	public String getName() {
		return null;
	}

	private static final long _SLEEP_TIME_MILS = 5000;

}
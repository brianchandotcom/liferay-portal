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

package com.liferay.dispatch.talend.internal;

import com.liferay.dispatch.executor.ScheduledTaskExecutor;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchLogLocalService;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.dispatch.talend.internal.helper.TalendScheduledTaskExecutorHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "scheduled.task.executor.type=" + TalendScheduledTaskExecutor.SCHEDULED_TASK_EXECUTOR_TYPE_TALEND,
	service = ScheduledTaskExecutor.class
)
public class TalendScheduledTaskExecutor implements ScheduledTaskExecutor {

	public static final String SCHEDULED_TASK_EXECUTOR_TYPE_TALEND = "talend";

	@Override
	public void execute(long dispatchTriggerId) throws PortalException {
		DispatchLog dispatchLog = null;
		DispatchTrigger dispatchTrigger = null;

		File error = null;
		File log = null;

		String rootDirectoryName = null;

		Date startDate = new Date();

		File tempFile = null;

		try {
			FileEntry fileEntry =
				_talendScheduledTaskExecutorHelper.getFileEntry(
					dispatchTriggerId);

			InputStream inputStream = fileEntry.getContentStream();

			tempFile = FileUtil.createTempFile(inputStream);

			File tempFolder = FileUtil.createTempFolder();

			FileUtil.unzip(tempFile, tempFolder);

			rootDirectoryName = tempFolder.getAbsolutePath();

			String shFileName = _getSHFileName(rootDirectoryName);

			_addExecutePermission(shFileName);

			dispatchTrigger = _dispatchTriggerLocalService.getDispatchTrigger(
				dispatchTriggerId);

			ProcessBuilder processBuilder = new ProcessBuilder(
				_getCommands(dispatchTrigger, rootDirectoryName, shFileName));

			error = FileUtil.createTempFile();
			log = FileUtil.createTempFile();

			processBuilder.redirectError(error);
			processBuilder.redirectOutput(log);

			dispatchLog = _dispatchLogLocalService.addDispatchLog(
				dispatchTrigger.getUserId(),
				dispatchTrigger.getDispatchTriggerId(), null, null, null,
				startDate, BackgroundTaskConstants.STATUS_IN_PROGRESS);

			Process process = processBuilder.start();

			process.waitFor();

			_dispatchLogLocalService.updateDispatchLog(
				dispatchLog.getDispatchLogId(), new Date(),
				FileUtil.read(error), FileUtil.read(log),
				BackgroundTaskConstants.STATUS_SUCCESSFUL);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			_dispatchLogLocalService.updateDispatchLog(
				dispatchLog.getDispatchLogId(), new Date(),
				exception.getMessage(), null,
				BackgroundTaskConstants.STATUS_FAILED);
		}
		finally {
			FileUtil.deltree(rootDirectoryName);

			if (error != null) {
				FileUtil.delete(error);
			}

			if (log != null) {
				FileUtil.delete(log);
			}

			if (tempFile != null) {
				FileUtil.delete(tempFile);
			}
		}
	}

	@Override
	public String getName() {
		return SCHEDULED_TASK_EXECUTOR_TYPE_TALEND;
	}

	private void _addExecutePermission(String shFileName) throws IOException {
		ProcessBuilder processBuilder = new ProcessBuilder(
			"chmod", "+x", shFileName);

		processBuilder.start();
	}

	private List<String> _getCommands(
		DispatchTrigger dispatchTrigger, String rootDirectoryName,
		String shFileName) {

		List<String> commands = new ArrayList<>();

		commands.add(shFileName);

		commands.add(
			"--context_param companyId=" + dispatchTrigger.getCompanyId());

		Date lastRunStateDate =
			_dispatchTriggerLocalService.getPreviousFireDate(
				dispatchTrigger.getDispatchTriggerId());

		if (lastRunStateDate != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");

			commands.add(
				"--context_param lastRunStartDate=" +
					simpleDateFormat.format(lastRunStateDate));
		}

		commands.add("--context_param jobWorkDirectory=" + rootDirectoryName);

		UnicodeProperties typeSettingsUnicodeProperties =
			dispatchTrigger.getTypeSettingsProperties();

		if (typeSettingsUnicodeProperties != null) {
			for (Map.Entry<String, String> propEntry :
					typeSettingsUnicodeProperties.entrySet()) {

				StringBundler contextSB = new StringBundler(4);

				contextSB.append("--context_param ");
				contextSB.append(propEntry.getKey());
				contextSB.append(StringPool.EQUAL);
				contextSB.append(propEntry.getValue());

				commands.add(contextSB.toString());
			}
		}

		return commands;
	}

	private String _getSHFileName(String rootDirectoryName) {
		String[] strings = FileUtil.find(rootDirectoryName, "**\\*.sh", null);

		return strings[0];
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TalendScheduledTaskExecutor.class);

	@Reference
	private DispatchLogLocalService _dispatchLogLocalService;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private TalendScheduledTaskExecutorHelper
		_talendScheduledTaskExecutorHelper;

}
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

package com.liferay.dispatch.talend.web.internal.executor;

import com.liferay.dispatch.executor.BaseDispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutor;
import com.liferay.dispatch.executor.DispatchTaskExecutorOutput;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.repository.exception.DispatchRepositoryException;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.process.CollectorOutputProcessor;
import com.liferay.petra.process.ConsumerOutputProcessor;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.ProcessUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Igor Beslic
 */
@Component(
	immediate = true,
	property = "dispatch.task.executor.type=" + TalendDispatchTaskExecutor.DISPATCH_TASK_EXECUTOR_TYPE_TALEND,
	service = DispatchTaskExecutor.class
)
public class TalendDispatchTaskExecutor extends BaseDispatchTaskExecutor {

	public static final String DISPATCH_TASK_EXECUTOR_TYPE_TALEND = "talend";

	public TalendDispatchTaskExecutor() {
	}

	@Override
	public void doExecute(
			DispatchTrigger dispatchTrigger,
			DispatchTaskExecutorOutput dispatchTaskExecutorOutput)
		throws IOException, PortalException {

		Path executablePath = getExecutablePath(
			dispatchTrigger.getDispatchTriggerId());

		_addExecutePermission(executablePath.toString());

		DispatchTalendCollectorOutputProcessor
			dispatchTalendCollectorOutputProcessor =
				new DispatchTalendCollectorOutputProcessor();

		try {
			Future<Map.Entry<byte[], byte[]>> future = ProcessUtil.execute(
				dispatchTalendCollectorOutputProcessor,
				_getArguments(dispatchTrigger, executablePath));

			Map.Entry<byte[], byte[]> entry = future.get();

			dispatchTaskExecutorOutput.setError(entry.getValue());
			dispatchTaskExecutorOutput.setOutput(entry.getKey());
		}
		catch (Exception exception) {
			dispatchTaskExecutorOutput.setError(
				dispatchTalendCollectorOutputProcessor._stdErrByteArray);

			throw new PortalException(exception);
		}
		finally {
			Path executableParentPath = executablePath.getParent();

			FileUtil.deltree(executableParentPath.toFile());
		}
	}

	@Override
	public String getName() {
		return DISPATCH_TASK_EXECUTOR_TYPE_TALEND;
	}

	protected TalendDispatchTaskExecutor(
		DispatchTriggerLocalService dispatchTriggerLocalService,
		DispatchFileRepository dispatchFileRepository) {

		_dispatchTriggerLocalService = dispatchTriggerLocalService;
		_dispatchFileRepository = dispatchFileRepository;
	}

	protected Path getExecutablePath(long dispatchTriggerId)
		throws IOException, PortalException {

		FileEntry fileEntry = _dispatchFileRepository.fetchFileEntry(
			dispatchTriggerId);

		if (fileEntry == null) {
			throw new DispatchRepositoryException(
				"Unable to get file entry for dispatch trigger ID " +
					dispatchTriggerId);
		}

		InputStream inputStream = fileEntry.getContentStream();

		File tempFile = FileUtil.createTempFile(inputStream);

		try {
			File tempFolder = FileUtil.createTempFolder();

			FileUtil.unzip(tempFile, tempFolder);

			String rootDirectoryName = tempFolder.getAbsolutePath();

			return Paths.get(
				rootDirectoryName, _getExecutableName(rootDirectoryName));
		}
		finally {
			if (tempFile != null) {
				FileUtil.delete(tempFile);
			}
		}
	}

	private void _addExecutePermission(String executablePath)
		throws PortalException {

		try {
			ProcessUtil.execute(
				ConsumerOutputProcessor.INSTANCE, "chmod", "+x",
				executablePath);
		}
		catch (ProcessException processException) {
			throw new PortalException(processException);
		}
	}

	private List<String> _getArguments(
		DispatchTrigger dispatchTrigger, Path executableFilePath) {

		List<String> arguments = new ArrayList<>();

		arguments.add("java");

		Path executableFileName = executableFilePath.getFileName();

		arguments.add(executableFileName.toString());

		arguments.add(
			"--context_param companyId=" + dispatchTrigger.getCompanyId());

		Date lastRunStateDate =
			_dispatchTriggerLocalService.getPreviousFireDate(
				dispatchTrigger.getDispatchTriggerId());

		if (lastRunStateDate != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss'Z'");

			arguments.add(
				"--context_param lastRunStartDate=" +
					simpleDateFormat.format(lastRunStateDate));
		}

		Path executableParentPath = executableFilePath.getParent();

		arguments.add(
			"--context_param jobWorkDirectory=" +
				executableParentPath.toString());

		UnicodeProperties taskSettingsUnicodeProperties =
			dispatchTrigger.getTaskSettingsUnicodeProperties();

		if (taskSettingsUnicodeProperties != null) {
			for (Map.Entry<String, String> propEntry :
					taskSettingsUnicodeProperties.entrySet()) {

				StringBundler contextSB = new StringBundler(4);

				contextSB.append("--context_param ");
				contextSB.append(propEntry.getKey());
				contextSB.append(StringPool.EQUAL);
				contextSB.append(propEntry.getValue());

				arguments.add(contextSB.toString());
			}
		}

		return arguments;
	}

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	private class DispatchTalendCollectorOutputProcessor
		extends CollectorOutputProcessor {

		@Override
		public byte[] processStdErr(InputStream stdErrInputStream)
			throws ProcessException {

			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			try {
				StreamUtil.transfer(
					stdErrInputStream, unsyncByteArrayOutputStream, false);
			}
			catch (IOException ioException) {
				throw new ProcessException(ioException);
			}

			_stdErrByteArray = unsyncByteArrayOutputStream.toByteArray();

			return _stdErrByteArray;
		}

		private byte[] _stdErrByteArray;

	}

}
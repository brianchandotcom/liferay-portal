/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.internal.scheduler;

import com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration;
import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.antivirus.async.store.constants.AntivirusAsyncDestinationNames;
import com.liferay.antivirus.async.store.internal.event.AntivirusAsyncEventListenerManager;
import com.liferay.antivirus.async.store.util.AntivirusAsyncUtil;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Date;
import java.util.Map;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	property = {"osgi.command.function=scan", "osgi.command.scope=antivirus"},
	service = SchedulerJobConfiguration.class
)
public class AntivirusAsyncFileStoreSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	public String getDestinationName() {
		return AntivirusAsyncDestinationNames.ANTIVIRUS_BATCH;
	}

	@Override
	public UnsafeConsumer<Message, Exception> getJobExecutorUnsafeConsumer() {
		return message -> scan((String)message.getPayload());
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		TriggerConfiguration triggerConfiguration =
			TriggerConfiguration.createTriggerConfiguration(
				_antivirusAsyncConfiguration.retryCronExpression());

		triggerConfiguration.setStartDate(
			new Date(
				System.currentTimeMillis() + TimeUnit.SECOND.toMillis(30)));

		return triggerConfiguration;
	}

	public void scan(String rootDirAbsolutePathString) {
		try {
			_scan(rootDirAbsolutePathString);
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_antivirusAsyncConfiguration = ConfigurableUtil.createConfigurable(
			AntivirusAsyncConfiguration.class, properties);
	}

	private void _scan(String rootDirAbsolutePathString) throws IOException {
		if (_log.isDebugEnabled()) {
			_log.debug("Scanning " + rootDirAbsolutePathString);
		}

		Path rootPath = Paths.get(rootDirAbsolutePathString);

		Files.walkFileTree(
			rootPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioException)
					throws IOException {

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					try {
						_scheduleAntivirusScan(rootPath, filePath);
					}
					catch (Throwable throwable) {
						_log.error(
							"Unable to schedule antivirus scan for " + filePath,
							throwable);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _scheduleAntivirusScan(Path rootPath, Path filePath) {
		Path relativePath = rootPath.relativize(filePath);

		// Company ID

		Path companyIdPath = relativePath.getName(0);

		long companyId = GetterUtil.getLong(companyIdPath.toString());

		relativePath = companyIdPath.relativize(relativePath);

		// Repository ID

		Path repositoryIdPath = relativePath.getName(0);

		long repositoryId = GetterUtil.getLong(repositoryIdPath.toString());

		if (repositoryId == AntivirusAsyncConstants.REPOSITORY_ID_QUARANTINE) {
			return;
		}

		relativePath = repositoryIdPath.relativize(relativePath);

		// Version label

		String versionLabel = String.valueOf(relativePath.getFileName());

		relativePath = relativePath.subpath(0, relativePath.getNameCount() - 1);

		String fileNameFragment = StringPool.BLANK;

		int x = versionLabel.lastIndexOf(CharPool.UNDERLINE);

		if (x > -1) {
			if (x > 0) {
				fileNameFragment = versionLabel.substring(0, x);
			}

			int y = versionLabel.lastIndexOf(CharPool.PERIOD);

			versionLabel = versionLabel.substring(x + 1, y);
		}

		// File name

		String fileName = String.valueOf(relativePath.getFileName());

		// Directory name

		String fileDirectory = StringPool.BLANK;

		if (relativePath.getNameCount() > 1) {
			fileDirectory = String.valueOf(
				relativePath.subpath(0, relativePath.getNameCount() - 1));
		}

		String fileExtension = _file.getExtension(fileName);

		if (fileExtension.equals("afsh")) {
			fileExtension = StringPool.BLANK;
			fileName = _file.stripExtension(fileName);
		}

		if (!fileNameFragment.isEmpty()) {
			String fileDirectoryParts = fileDirectory.replaceAll(
				StringPool.SLASH, StringPool.BLANK);

			if (fileName.startsWith(fileDirectoryParts)) {
				fileDirectory = StringPool.BLANK;
			}
		}

		if (!fileDirectory.isEmpty()) {
			fileName = StringBundler.concat(
				fileDirectory, StringPool.SLASH, fileName);

			if (fileNameFragment.isEmpty()) {
				fileName += StringPool.SLASH;
			}
		}

		Message message = new Message();

		message.put("companyId", companyId);
		message.put("fileExtension", fileExtension);
		message.put("fileName", fileName);
		message.put(
			"jobName",
			AntivirusAsyncUtil.getJobName(
				companyId, repositoryId, fileName, versionLabel));
		message.put("repositoryId", repositoryId);

		try {
			long size = -1;

			if (Files.exists(filePath)) {
				size = Files.size(filePath);
			}

			message.put("size", size);
		}
		catch (IOException ioException) {
			_log.error(ioException);
		}

		message.put("userId", 0L);
		message.put("versionLabel", versionLabel);

		_antivirusAsyncEventListenerManager.onPrepare(message);

		_messageBus.sendMessage(
			AntivirusAsyncDestinationNames.ANTIVIRUS, message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AntivirusAsyncFileStoreSchedulerJobConfiguration.class);

	private AntivirusAsyncConfiguration _antivirusAsyncConfiguration;

	@Reference
	private AntivirusAsyncEventListenerManager
		_antivirusAsyncEventListenerManager;

	@Reference
	private File _file;

	@Reference
	private MessageBus _messageBus;

	@Reference(target = "(rootDir=*)")
	private ServiceReference<Store> _storeServiceReference;

}
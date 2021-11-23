/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.antivirus.async.store.internal.file.store;

import com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration;
import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.antivirus.async.store.internal.events.AntivirusAsyncEventListenerManager;
import com.liferay.antivirus.async.store.util.AntivirusAsyncUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.time.Instant;

import java.util.Date;
import java.util.Map;
import java.util.function.Consumer;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = {
		"destination.name=" + AntivirusAsyncConstants.ANTIVIRUS_BATCH_DESTINATION,
		"file.system.store=advanced", "file.system.store=default",
		"osgi.command.function=scan", "osgi.command.scope=antivirus"
	},
	service = {Consumer.class, MessageListener.class}
)
public class AntivirusAsyncFileStoreInitializer
	implements Consumer<File>, MessageListener {

	@Activate
	public AntivirusAsyncFileStoreInitializer(
		BundleContext bundleContext, Map<String, Object> properties,
		@Reference DestinationFactory destinationFactory) {

		AntivirusAsyncConfiguration antivirusAsyncConfiguration =
			ConfigurableUtil.createConfigurable(
				AntivirusAsyncConfiguration.class, properties);

		_batchInterval = antivirusAsyncConfiguration.batchScanInterval();

		DestinationConfiguration destinationConfiguration =
			DestinationConfiguration.createSerialDestinationConfiguration(
				AntivirusAsyncConstants.ANTIVIRUS_BATCH_DESTINATION);

		destinationConfiguration.setMaximumQueueSize(1);

		Destination destination = destinationFactory.createDestination(
			destinationConfiguration);

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Override
	public void accept(File rootDir) {
		String rootDirAbsolutePath = rootDir.getAbsolutePath();

		if (_log.isDebugEnabled()) {
			_log.debug("Initializing " + rootDirAbsolutePath);
		}

		try {
			SchedulerResponse schedulerResponse =
				_schedulerEngineHelper.getScheduledJob(
					rootDirAbsolutePath,
					AntivirusAsyncConstants.ANTIVIRUS_BATCH_GROUP_NAME,
					StorageType.PERSISTED);

			if (schedulerResponse != null) {
				_schedulerEngineHelper.delete(
					rootDirAbsolutePath, schedulerResponse.getGroupName(),
					schedulerResponse.getStorageType());
			}

			Trigger trigger = _createTrigger(rootDirAbsolutePath);

			_schedulerEngineHelper.schedule(
				trigger, StorageType.PERSISTED, null,
				AntivirusAsyncConstants.ANTIVIRUS_BATCH_DESTINATION,
				rootDirAbsolutePath, 0);
		}
		catch (SchedulerException schedulerException) {
			ReflectionUtil.throwException(schedulerException);
		}
	}

	@Override
	public void receive(Message message) throws MessageListenerException {
		String rootDirAbsolutePath = (String)message.getPayload();

		scan(rootDirAbsolutePath);
	}

	public void scan(String rootDirAbsolutePath) {
		if (_log.isDebugEnabled()) {
			_log.debug("Batch scanning " + rootDirAbsolutePath);
		}

		Path rootPath = Paths.get(rootDirAbsolutePath);

		try {
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
							Path filePath,
							BasicFileAttributes basicFileAttributes)
						throws IOException {

						try {
							_scheduleAntivirusScan(rootPath, filePath);
						}
						catch (Throwable throwable) {
							_log.error(
								StringBundler.concat(
									"Error occured scheduling antivirus batch ",
									"scan for file ", filePath),
								throwable);
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			ReflectionUtil.throwException(ioException);
		}
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private Trigger _createTrigger(String jobName) {
		String cronExpression = StringBundler.concat(
			"0 0 0/", _batchInterval, " * * ?");

		Instant now = Instant.now();

		return _triggerFactory.createTrigger(
			jobName, AntivirusAsyncConstants.ANTIVIRUS_BATCH_GROUP_NAME,
			Date.from(now.plusSeconds(30)), null, cronExpression);
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

		if (repositoryId == AntivirusAsyncConstants.QUARANTINE_REPOSITORY_ID) {
			return;
		}

		relativePath = repositoryIdPath.relativize(relativePath);

		// Version Label

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

		String fileExtension = FileUtil.getExtension(fileName);

		if (fileExtension.equals("afsh")) {
			fileExtension = StringPool.BLANK;
			fileName = FileUtil.stripExtension(fileName);
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
		message.put("repositoryId", repositoryId);

		try {
			long size = -1;

			if (Files.exists(filePath)) {
				size = Files.size(filePath);
			}

			message.put("size", size);
		}
		catch (IOException ioException) {
			_log.error(ioException, ioException);
		}

		message.put("versionLabel", versionLabel);

		message.put("userId", 0L);

		message.put(
			"jobName",
			AntivirusAsyncUtil.getJobName(
				companyId, repositoryId, fileName, versionLabel));

		_antivirusAsyncEventListenerManager.onPrepare(message);

		_messageBus.sendMessage(
			AntivirusAsyncConstants.ANTIVIRUS_DESTINATION, message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AntivirusAsyncFileStoreInitializer.class);

	@Reference
	private AntivirusAsyncEventListenerManager
		_antivirusAsyncEventListenerManager;

	private final int _batchInterval;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private final ServiceRegistration<Destination> _serviceRegistration;

	@Reference
	private TriggerFactory _triggerFactory;

}
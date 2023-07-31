/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.blogs.web.internal.optimizer;

import com.liferay.adaptive.media.constants.AMOptimizeImagesBackgroundTaskConstants;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.optimizer.AMImageOptimizer;
import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLFileEntryConfiguration",
	property = "adaptive.media.key=blogs", service = AMImageOptimizer.class
)
public class BlogsAMImageOptimizer implements AMImageOptimizer {

	@Override
	public void optimize(long companyId) {
		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				companyId);

		int count = _amImageCounter.countExpectedAMImageEntries(companyId);

		int total = count * amImageConfigurationEntries.size();

		AtomicInteger successCounter = new AtomicInteger(0);
		AtomicInteger errorCounter = new AtomicInteger(0);

		for (AMImageConfigurationEntry amImageConfigurationEntry :
				amImageConfigurationEntries) {

			_optimize(
				companyId, amImageConfigurationEntry.getUUID(), total,
				successCounter, errorCounter);
		}
	}

	@Override
	public void optimize(long companyId, String configurationEntryUuid) {
		int total = _amImageCounter.countExpectedAMImageEntries(companyId);

		AtomicInteger successCounter = new AtomicInteger(0);
		AtomicInteger errorCounter = new AtomicInteger(0);

		_optimize(
			companyId, configurationEntryUuid, total, successCounter,
			errorCounter);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlFileEntryConfiguration = ConfigurableUtil.createConfigurable(
			DLFileEntryConfiguration.class, properties);
	}

	private void _optimize(
		long companyId, String configurationEntryUuid, int total,
		AtomicInteger successCounter, AtomicInteger errorCounter) {

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				dynamicQuery.add(
					classNameIdProperty.eq(
						_classNameLocalService.getClassNameId(
							BlogsEntry.class.getName())));

				Property mimeTypeProperty = PropertyFactoryUtil.forName(
					"mimeType");

				dynamicQuery.add(
					mimeTypeProperty.in(
						_amImageMimeTypeProvider.getSupportedMimeTypes()));

				Property sizeProperty = PropertyFactoryUtil.forName("size");

				dynamicQuery.add(
					sizeProperty.le(
						_dlFileEntryConfiguration.
							previewableProcessorMaxSize()));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(DLFileEntry dlFileEntry) -> {
				FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

				try {
					_amProcessor.process(
						fileEntry.getFileVersion(), configurationEntryUuid);

					_sendStatusMessage(
						successCounter.incrementAndGet(), errorCounter.get(),
						total);
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to process file entry " +
								fileEntry.getFileEntryId(),
							exception);
					}

					_sendStatusMessage(
						successCounter.get(), errorCounter.incrementAndGet(),
						total);
				}
			});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private void _sendStatusMessage(int count, int errors, int total) {
		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());

		Class<? extends BlogsAMImageOptimizer> clazz = getClass();

		message.put(
			AMOptimizeImagesBackgroundTaskConstants.CLASS_NAME,
			clazz.getName());

		message.put(AMOptimizeImagesBackgroundTaskConstants.COUNT, count);
		message.put(AMOptimizeImagesBackgroundTaskConstants.ERRORS, errors);
		message.put(AMOptimizeImagesBackgroundTaskConstants.TOTAL, total);

		message.put("status", BackgroundTaskConstants.STATUS_IN_PROGRESS);

		_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsAMImageOptimizer.class);

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Reference(target = "(adaptive.media.key=blogs)")
	private AMImageCounter _amImageCounter;

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AMProcessor<FileVersion> _amProcessor;

	@Reference
	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private volatile DLFileEntryConfiguration _dlFileEntryConfiguration;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}
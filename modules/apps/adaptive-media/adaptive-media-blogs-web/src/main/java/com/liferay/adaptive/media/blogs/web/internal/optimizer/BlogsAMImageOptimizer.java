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

package com.liferay.adaptive.media.blogs.web.internal.optimizer;

import com.liferay.adaptive.media.constants.AMOptimizeImagesBackgroundTaskConstants;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.optimizer.AMImageOptimizer;
import com.liferay.adaptive.media.image.processor.AMImageProcessor;
import com.liferay.adaptive.media.image.size.AMImageSizeProvider;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
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
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true, property = "adaptive.media.key=blogs",
	service = AMImageOptimizer.class
)
public class BlogsAMImageOptimizer implements AMImageOptimizer {

	@Override
	public void optimize(long companyId) {
		Collection<AMImageConfigurationEntry> amImageConfigurationEntries =
			_amImageConfigurationHelper.getAMImageConfigurationEntries(
				companyId);

		int count = _amImageCounter.countExpectedAMImageEntries(companyId);

		int total = count * amImageConfigurationEntries.size();

		AtomicInteger atomicCounter = new AtomicInteger(0);

		for (AMImageConfigurationEntry amImageConfigurationEntry :
				amImageConfigurationEntries) {

			_optimize(
				companyId, amImageConfigurationEntry.getUUID(), total,
				atomicCounter);
		}
	}

	@Override
	public void optimize(long companyId, String configurationEntryUuid) {
		int total = _amImageCounter.countExpectedAMImageEntries(companyId);

		_optimize(
			companyId, configurationEntryUuid, total, new AtomicInteger(0));
	}

	private void _optimize(
		long companyId, String configurationEntryUuid, int total,
		AtomicInteger atomicCounter) {

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				long classNameId = _classNameLocalService.getClassNameId(
					BlogsEntry.class.getName());

				dynamicQuery.add(classNameIdProperty.eq(classNameId));

				Property mimeTypeProperty = PropertyFactoryUtil.forName(
					"mimeType");

				dynamicQuery.add(
					mimeTypeProperty.in(
						_amImageMimeTypeProvider.getSupportedMimeTypes()));

				Property sizeProperty = PropertyFactoryUtil.forName("size");

				dynamicQuery.add(
					sizeProperty.le(_amImageSizeProvider.getImageMaxSize()));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(DLFileEntry dlFileEntry) -> {
				FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

				try {
					_amImageProcessor.process(
						fileEntry.getFileVersion(), configurationEntryUuid);

					_sendStatusMessage(atomicCounter.incrementAndGet(), total);
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to process file entry " +
							fileEntry.getFileEntryId(),
						portalException);
				}
			});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private void _sendStatusMessage(int count, int total) {
		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());

		Class<? extends BlogsAMImageOptimizer> clazz = getClass();

		message.put(
			AMOptimizeImagesBackgroundTaskConstants.CLASS_NAME,
			clazz.getName());

		message.put(AMOptimizeImagesBackgroundTaskConstants.COUNT, count);
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
	private AMImageProcessor _amImageProcessor;

	@Reference
	private AMImageSizeProvider _amImageSizeProvider;

	@Reference
	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}
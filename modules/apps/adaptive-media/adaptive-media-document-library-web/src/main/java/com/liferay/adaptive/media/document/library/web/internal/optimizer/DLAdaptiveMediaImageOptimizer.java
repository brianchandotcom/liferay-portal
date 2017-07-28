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

package com.liferay.adaptive.media.document.library.web.internal.optimizer;

import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationHelper;
import com.liferay.adaptive.media.image.constants.AdaptiveMediaImageConstants;
import com.liferay.adaptive.media.image.counter.AdaptiveMediaImageCounter;
import com.liferay.adaptive.media.image.optimizer.AdaptiveMediaImageOptimizer;
import com.liferay.adaptive.media.image.processor.AdaptiveMediaImageProcessor;
import com.liferay.adaptive.media.web.constants.OptimizeImagesBackgroundTaskConstants;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageSender;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskThreadLocal;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.trash.kernel.service.TrashEntryLocalService;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true, property = {"adaptive.media.key=document-library"},
	service = AdaptiveMediaImageOptimizer.class
)
public class DLAdaptiveMediaImageOptimizer
	implements AdaptiveMediaImageOptimizer {

	@Override
	public void optimize(long companyId) {
		Collection<AdaptiveMediaImageConfigurationEntry> configurationEntries =
			_adaptiveMediaImageConfigurationHelper.
				getAdaptiveMediaImageConfigurationEntries(companyId);

		int count =
			_adaptiveMediaImageCounter.countExpectedAdaptiveMediaImageEntries(
				companyId);

		int total = count * configurationEntries.size();

		final AtomicInteger atomicCounter = new AtomicInteger(0);

		for (AdaptiveMediaImageConfigurationEntry configurationEntry :
				configurationEntries) {

			_optimize(
				companyId, configurationEntry.getUUID(), total, atomicCounter);
		}
	}

	@Override
	public void optimize(long companyId, String configurationEntryUuid) {
		int total =
			_adaptiveMediaImageCounter.countExpectedAdaptiveMediaImageEntries(
				companyId);

		final AtomicInteger atomiCounter = new AtomicInteger(0);

		_optimize(companyId, configurationEntryUuid, total, atomiCounter);
	}

	private void _optimize(
		long companyId, String configurationEntryUuid, int total,
		AtomicInteger atomicCounter) {

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					DynamicQuery trashEntryDynamicQuery =
						_trashEntryLocalService.dynamicQuery();

					trashEntryDynamicQuery.setProjection(
						ProjectionFactoryUtil.property("classPK"));

					Property companyIdProperty = PropertyFactoryUtil.forName(
						"companyId");

					trashEntryDynamicQuery.add(companyIdProperty.eq(companyId));

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					trashEntryDynamicQuery.add(
						classNameIdProperty.eq(
							_classNameLocalService.getClassNameId(
								DLFileEntry.class)));

					dynamicQuery.add(companyIdProperty.eq(companyId));

					Property groupIdProperty = PropertyFactoryUtil.forName(
						"groupId");
					Property repositoryIdProperty = PropertyFactoryUtil.forName(
						"repositoryId");

					dynamicQuery.add(
						groupIdProperty.eqProperty(repositoryIdProperty));

					Property mimeTypeProperty = PropertyFactoryUtil.forName(
						"mimeType");

					dynamicQuery.add(
						mimeTypeProperty.in(
							AdaptiveMediaImageConstants.
								getSupportedMimeTypes()));

					Property fileEntryIdProperty = PropertyFactoryUtil.forName(
						"fileEntryId");

					dynamicQuery.add(
						fileEntryIdProperty.notIn(trashEntryDynamicQuery));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DLFileEntry>() {

				@Override
				public void performAction(DLFileEntry dlFileEntry)
					throws PortalException {

					FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);

					try {
						_adaptiveMediaImageProcessor.process(
							fileEntry.getFileVersion(), configurationEntryUuid);

						_sendStatusMessage(
							atomicCounter.incrementAndGet(), total);
					}
					catch (PortalException pe) {
						_log.error(
							"Unable to process file entry id " +
								fileEntry.getFileEntryId(),
							pe);
					}
				}

			});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}
	}

	private void _sendStatusMessage(int count, int total) {
		Message message = new Message();

		message.put(
			BackgroundTaskConstants.BACKGROUND_TASK_ID,
			BackgroundTaskThreadLocal.getBackgroundTaskId());

		Class<? extends DLAdaptiveMediaImageOptimizer> clazz = getClass();

		message.put(
			OptimizeImagesBackgroundTaskConstants.CLASS_NAME, clazz.getName());

		message.put(OptimizeImagesBackgroundTaskConstants.COUNT, count);
		message.put(OptimizeImagesBackgroundTaskConstants.TOTAL, total);

		message.put("status", BackgroundTaskConstants.STATUS_IN_PROGRESS);

		_backgroundTaskStatusMessageSender.sendBackgroundTaskStatusMessage(
			message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLAdaptiveMediaImageOptimizer.class);

	@Reference
	private AdaptiveMediaImageConfigurationHelper
		_adaptiveMediaImageConfigurationHelper;

	@Reference(target = "(adaptive.media.key=document-library)")
	private AdaptiveMediaImageCounter _adaptiveMediaImageCounter;

	@Reference
	private AdaptiveMediaImageProcessor _adaptiveMediaImageProcessor;

	@Reference
	private BackgroundTaskStatusMessageSender
		_backgroundTaskStatusMessageSender;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

}
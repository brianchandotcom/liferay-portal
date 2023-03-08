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

package com.liferay.segments.internal.messaging;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.segments.configuration.SegmentsConfiguration;
import com.liferay.segments.internal.constants.SegmentsDestinationNames;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.segments.configuration.SegmentsConfiguration",
	service = SchedulerJobConfiguration.class
)
public class SegmentsEntryRelIndexerMessageListener
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
			ActionableDynamicQuery actionableDynamicQuery =
				_segmentsEntryLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> {
					Property activeProperty = PropertyFactoryUtil.forName(
						"active");

					dynamicQuery.add(activeProperty.eq(true));
				});
			actionableDynamicQuery.setPerformActionMethod(
				(ActionableDynamicQuery.PerformActionMethod<SegmentsEntry>)
					this::_reindex);

			actionableDynamicQuery.performActions();
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_segmentsConfiguration.segmentsPreviewCheckInterval(),
			TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_segmentsConfiguration = ConfigurableUtil.createConfigurable(
			SegmentsConfiguration.class, properties);
	}

	private void _reindex(SegmentsEntry segmentsEntry) {
		Message message = new Message();

		message.put("companyId", segmentsEntry.getCompanyId());
		message.put("segmentsEntryId", segmentsEntry.getSegmentsEntryId());
		message.put("type", segmentsEntry.getType());

		_messageBus.sendMessage(
			SegmentsDestinationNames.SEGMENTS_ENTRY_REINDEX, message);
	}

	@Reference
	private MessageBus _messageBus;

	private volatile SegmentsConfiguration _segmentsConfiguration;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}
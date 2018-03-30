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

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.exporter.UADEntityExporter;
import com.liferay.user.associated.data.web.internal.display.UADApplicationExportDisplay;
import com.liferay.user.associated.data.web.internal.export.background.task.UADExportBackgroundTaskManagerUtil;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = UADApplicationExportHelper.class)
public class UADApplicationExportHelper {

	public int getApplicationDataCount(String applicationName, long userId) {
		List<UADEntityExporter> uadEntityExporters =
			getApplicationUADEntityExporters(applicationName);

		Stream<UADEntityExporter> uadEntityExporterStream =
			uadEntityExporters.stream();

		return uadEntityExporterStream.mapToInt(
			uadEntityExporter -> {
				try {
					return (int)uadEntityExporter.count(userId);
				}
				catch (PortalException pe) {
					_log.error(pe, pe);

					return 0;
				}
			}).sum();
	}

	public Date getApplicationLastExportDate(
		String applicationName, long groupId, long userId) {

		BackgroundTask backgroundTask =
			UADExportBackgroundTaskManagerUtil.fetchLastBackgroundTask(
				applicationName, groupId, userId,
				BackgroundTaskConstants.STATUS_SUCCESSFUL);

		if (backgroundTask != null) {
			return backgroundTask.getCompletionDate();
		}

		return null;
	}

	public List<String> getApplicationNames() {
		Collection<UADEntityAggregator> uadEntityAggregators =
			_uadRegistry.getUADEntityAggregators();

		Stream<UADEntityAggregator> uadEntityAggregatorStream =
			uadEntityAggregators.stream();

		return uadEntityAggregatorStream.map(
			UADEntityAggregator::getApplicationName
		).distinct(
		).sorted(
		).collect(
			Collectors.toList()
		);
	}

	public List<UADEntityExporter> getApplicationUADEntityExporters(
		String applicationName) {

		Stream<UADEntityDisplay> uadEntityDisplayStream =
			_uadRegistry.getUADEntityDisplayStream();

		return uadEntityDisplayStream.filter(
			uadEntityDisplay -> applicationName.equals(
				uadEntityDisplay.getApplicationName())
		).map(
			uadEntityDisplay -> uadEntityDisplay.getKey()
		).map(
			key -> _uadRegistry.getUADEntityExporter(key)
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	public UADApplicationExportDisplay getUADApplicationExportDisplay(
		String applicationName, long groupId, long userId) {

		List<UADEntityExporter> uadEntityExporters =
			getApplicationUADEntityExporters(applicationName);

		return new UADApplicationExportDisplay(
			applicationName, getApplicationDataCount(applicationName, userId),
			ListUtil.isNotEmpty(uadEntityExporters),
			getApplicationLastExportDate(applicationName, groupId, userId));
	}

	public List<UADApplicationExportDisplay> getUADApplicationExportDisplays(
		long groupId, long userId) {

		List<String> applicationNames = getApplicationNames();

		Stream<String> applicationNameStream = applicationNames.stream();

		return applicationNameStream.map(
			applicationName ->
				getUADApplicationExportDisplay(applicationName, groupId, userId)
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UADApplicationExportHelper.class);

	@Reference
	private Portal _portal;

	@Reference
	private UADRegistry _uadRegistry;

}
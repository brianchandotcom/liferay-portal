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
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.web.internal.display.UADApplicationExportDisplay;
import com.liferay.user.associated.data.web.internal.export.background.task.UADExportBackgroundTaskManagerUtil;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.Collection;
import java.util.Date;
import java.util.List;
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
		List<UADExporter> uadExporters = getApplicationUADExporters(
			applicationName);

		Stream<UADExporter> uadExporterStream = uadExporters.stream();

		return uadExporterStream.mapToInt(
			uadExporter -> {
				try {
					return (int)uadExporter.count(userId);
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

	public Stream<String> getApplicationNames() {
		Collection<UADExporter> uadExporters = _uadRegistry.getUADExporters();

		Stream<UADExporter> uadExporterStream = uadExporters.stream();

		return uadExporterStream.map(
			UADExporter::getApplicationName
		).distinct(
		).sorted();
	}

	public List<UADExporter> getApplicationUADExporters(
		String applicationName) {

		Collection<UADExporter> uadExporters = _uadRegistry.getUADExporters();

		Stream<UADExporter> uadExporterStream = uadExporters.stream();

		return uadExporterStream.filter(
			uadExporter -> applicationName.equals(
				uadExporter.getApplicationName())
		).collect(
			Collectors.toList()
		);
	}

	public UADApplicationExportDisplay getUADApplicationExportDisplay(
		String applicationName, long groupId, long userId) {

		List<UADExporter> uadExporters = getApplicationUADExporters(
			applicationName);

		return new UADApplicationExportDisplay(
			applicationName, getApplicationDataCount(applicationName, userId),
			ListUtil.isNotEmpty(uadExporters),
			getApplicationLastExportDate(applicationName, groupId, userId));
	}

	public List<UADApplicationExportDisplay> getUADApplicationExportDisplays(
		long groupId, long userId) {

		Stream<String> applicationNameStream = getApplicationNames();

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
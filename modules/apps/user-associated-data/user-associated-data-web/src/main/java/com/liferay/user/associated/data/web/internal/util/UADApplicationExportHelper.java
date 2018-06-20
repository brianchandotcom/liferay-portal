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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.exporter.UADExporter;
import com.liferay.user.associated.data.web.internal.display.UADApplicationExportDisplay;
import com.liferay.user.associated.data.web.internal.export.background.task.UADExportBackgroundTaskManagerUtil;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = UADApplicationExportHelper.class)
public class UADApplicationExportHelper {

	public Date getApplicationLastExportDate(
		String applicationKey, long groupId, long userId) {

		BackgroundTask backgroundTask =
			UADExportBackgroundTaskManagerUtil.fetchLastBackgroundTask(
				applicationKey, groupId, userId,
				BackgroundTaskConstants.STATUS_SUCCESSFUL);

		if (backgroundTask != null) {
			return backgroundTask.getCompletionDate();
		}

		return null;
	}

	public UADApplicationExportDisplay getUADApplicationExportDisplay(
		String applicationKey, long groupId, long userId) {

		Stream<UADDisplay> uadDisplayStream =
			_uadRegistry.getApplicationUADDisplayStream(applicationKey);

		List<UADExporter> uadExporters = uadDisplayStream.map(
			UADDisplay::getTypeClass
		).map(
			Class::getName
		).map(
			key -> _uadRegistry.getUADExporter(key)
		).collect(
			Collectors.toList()
		);

		int applicationDataCount = 0;

		for (UADExporter uadExporter : uadExporters) {
			try {
				applicationDataCount += (int)uadExporter.count(userId);
			}
			catch (PortalException pe) {
				_log.error(pe, pe);
			}
		}

		return new UADApplicationExportDisplay(
			applicationKey, applicationDataCount, !uadExporters.isEmpty(),
			getApplicationLastExportDate(applicationKey, groupId, userId));
	}

	public List<UADApplicationExportDisplay> getUADApplicationExportDisplays(
		long groupId, long userId) {

		Set<String> applicationUADDisplayKeySet =
			_uadRegistry.getApplicationUADDisplaysKeySet();

		Iterator<String> iterator = applicationUADDisplayKeySet.iterator();

		List<UADApplicationExportDisplay> uadApplicationExportDisplays =
			new ArrayList<>();

		while (iterator.hasNext()) {
			String applicationKey = iterator.next();

			uadApplicationExportDisplays.add(
				getUADApplicationExportDisplay(
					applicationKey, groupId, userId));
		}

		Stream<UADApplicationExportDisplay> uadApplicationExportDisplayStream =
			uadApplicationExportDisplays.stream();

		return uadApplicationExportDisplayStream.sorted(
			Comparator.comparing(UADApplicationExportDisplay::getApplicationKey)
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
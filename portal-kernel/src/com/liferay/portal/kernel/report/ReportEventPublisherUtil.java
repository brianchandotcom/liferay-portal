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

package com.liferay.portal.kernel.report;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Jonathan McCann
 */
public class ReportEventPublisherUtil {

	public static void addError(String className, String error) {
		_reportEventPublisher.addError(className, error);
	}

	public static void addEvent(String className, long duration) {
		_reportEventPublisher.addEvent(className, duration);
	}

	public static void addWarning(String className, String warning) {
		_reportEventPublisher.addWarning(className, warning);
	}

	public static void generateUpgradeReport() {
		_reportEventPublisher.generateUpgradeReport();
	}

	private static volatile ReportEventPublisher _reportEventPublisher =
		ServiceProxyFactory.newServiceTrackedInstance(
			ReportEventPublisher.class, ReportEventPublisherUtil.class,
			"_reportEventPublisher", false);

}
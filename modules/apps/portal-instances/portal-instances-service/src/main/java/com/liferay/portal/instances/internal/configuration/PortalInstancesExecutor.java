/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.internal.configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Raymond Augé
 */
@Component(service = PortalInstancesExecutor.class)
public class PortalInstancesExecutor {

	@Activate
	public PortalInstancesExecutor() {
		_scheduledExecutorService =
			Executors.newSingleThreadScheduledExecutor();
	}

	public ScheduledFuture<?> schedule(
		Runnable runnable, long delay, TimeUnit unit) {

		return _scheduledExecutorService.schedule(runnable, delay, unit);
	}

	@Deactivate
	protected void deactivate() {
		_scheduledExecutorService.shutdown();
	}

	private final ScheduledExecutorService _scheduledExecutorService;

}
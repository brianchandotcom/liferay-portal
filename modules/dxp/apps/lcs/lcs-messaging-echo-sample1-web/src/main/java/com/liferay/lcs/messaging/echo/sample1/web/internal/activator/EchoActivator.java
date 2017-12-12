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

package com.liferay.lcs.messaging.echo.sample1.web.internal.activator;

import com.liferay.lcs.messaging.bus.LCSMessageBusService;
import com.liferay.lcs.messaging.bus.LCSMessageListener;
import com.liferay.lcs.messaging.echo.sample1.web.internal.messaging.EchoLCSMessageListener;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true)
public class EchoActivator {

	@Activate
	public void start() {
		_lcsMessageListener = new EchoLCSMessageListener(_lcsMessageBusService);

		_lcsMessageBusService.registerLCSMessageListener(
			"lcs_echo", _lcsMessageListener);
	}

	@Deactivate
	public void stop() {
		_lcsMessageBusService.unregisterLCSMessageListener(
			"lcs_echo", _lcsMessageListener);
	}

	@Reference
	private LCSMessageBusService _lcsMessageBusService;

	private LCSMessageListener _lcsMessageListener;

}
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.nio.intraband;

import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.concurrent.Executor;

/**
 * @author Shuyang Zhou
 */
public abstract class BaseAsyncDatagramReceiveHandler
	implements DatagramReceiveHandler {

	public BaseAsyncDatagramReceiveHandler() {
		Class<? extends BaseAsyncDatagramReceiveHandler> clazz = getClass();

		_executor = PortalExecutorManagerUtil.getPortalExecutor(
			clazz.getName());
	}

	public void receive(
		RegistrationReference registrationReference, Datagram datagram) {

		_executor.execute(new DispatchJob(registrationReference, datagram));
	}

	protected abstract void doReceive(
			RegistrationReference registrationReference, Datagram datagram)
		throws Exception;

	private static Log _log = LogFactoryUtil.getLog(
		BaseAsyncDatagramReceiveHandler.class);

	private final Executor _executor;

	private class DispatchJob implements Runnable {

		public DispatchJob(
			RegistrationReference registrationReference, Datagram datagram) {

			_datagram = datagram;
			_registrationReference = registrationReference;
		}

		public void run() {
			try {
				doReceive(_registrationReference, _datagram);
			}
			catch (Exception e) {
				_log.error("Unable to dispatch", e);
			}
		}

		private final Datagram _datagram;
		private final RegistrationReference _registrationReference;

	}

}
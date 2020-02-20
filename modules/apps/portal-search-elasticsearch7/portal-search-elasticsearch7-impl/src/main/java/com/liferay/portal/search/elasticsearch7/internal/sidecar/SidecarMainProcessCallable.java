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

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.process.local.LocalProcessLauncher;

import java.io.Serializable;

/**
 * @author Tina Tian
 */
public class SidecarMainProcessCallable
	implements ProcessCallable<Serializable> {

	public SidecarMainProcessCallable(long heartbeatInterval) {
		_heartbeatInterval = heartbeatInterval;
	}

	@Override
	public Serializable call() throws ProcessException {
		LocalProcessLauncher.ProcessContext.attach(
			"SidecarMainProcessCallable", _heartbeatInterval,
			(shutdownCode, shutdownThrowable) -> {
				ElasticsearchServerUtil.shutdown();

				return true;
			});

		try {
			ElasticsearchServerUtil.waitForShutdown();
		}
		catch (InterruptedException interruptedException) {
			throw new ProcessException(
				"Sidecar main thread is interrupted", interruptedException);
		}

		return null;
	}

	private static final long serialVersionUID = 1L;

	private final long _heartbeatInterval;

}
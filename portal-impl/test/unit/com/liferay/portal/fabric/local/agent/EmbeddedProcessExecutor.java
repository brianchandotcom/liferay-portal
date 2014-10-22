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

package com.liferay.portal.fabric.local.agent;

import com.liferay.portal.fabric.local.worker.EmbeddedProcessChannel;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessChannel;
import com.liferay.portal.kernel.process.ProcessConfig;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.process.ProcessExecutor;

import java.io.Serializable;

import java.util.concurrent.Callable;

/**
 * @author Shuyang Zhou
 */
public class EmbeddedProcessExecutor implements ProcessExecutor {

	@Override
	public <T extends Serializable> ProcessChannel<T> execute(
		ProcessConfig processConfig, final ProcessCallable<T> processCallable) {

		return new EmbeddedProcessChannel<T>(
			new DefaultNoticeableFuture<T>(
				new Callable<T>() {

					@Override
					public T call() throws ProcessException {
						return processCallable.call();
					}

				}));
	}

}
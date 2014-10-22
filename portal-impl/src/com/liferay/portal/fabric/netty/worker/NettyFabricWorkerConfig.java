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

package com.liferay.portal.fabric.netty.worker;

import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessConfig;

import java.io.Serializable;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricWorkerConfig<T extends Serializable>
	implements Serializable {

	public NettyFabricWorkerConfig(
		long id, ProcessConfig processConfig,
		ProcessCallable<T> processCallable) {

		if (processConfig == null) {
			throw new NullPointerException("Process config is null");
		}

		if (processCallable == null) {
			throw new NullPointerException("Process callable is null");
		}

		_id = id;
		_processConfig = processConfig;
		_processCallable = processCallable;
	}

	public long getId() {
		return _id;
	}

	public ProcessCallable<T> getProcessCallable() {
		return _processCallable;
	}

	public ProcessConfig getProcessConfig() {
		return _processConfig;
	}

	private static final long serialVersionUID = 1L;

	private final long _id;
	private final ProcessCallable<T> _processCallable;
	private final ProcessConfig _processConfig;

}
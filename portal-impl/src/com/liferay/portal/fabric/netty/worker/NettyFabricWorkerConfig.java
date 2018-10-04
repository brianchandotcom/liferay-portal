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

import com.liferay.petra.process.PathHolder;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.process.ProcessException;
import com.liferay.util.SerializableUtil;

import java.io.Serializable;

import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricWorkerConfig<T extends Serializable>
	implements Serializable {

	public NettyFabricWorkerConfig(
		long id, ProcessConfig processConfig,
		ProcessCallable<T> processCallable, Map<Path, Path> inputPathMap) {

		if (processConfig == null) {
			throw new NullPointerException("Process config is null");
		}

		if (processCallable == null) {
			throw new NullPointerException("Process callable is null");
		}

		if (inputPathMap == null) {
			throw new NullPointerException("Input path map is null");
		}

		_id = id;
		_processConfig = processConfig;
		_processCallable = new NettyFabricWorkerProcessCallable<>(
			processCallable);

		_inputPathHolderMap = new HashMap<>();

		for (Map.Entry<Path, Path> entry : inputPathMap.entrySet()) {
			_inputPathHolderMap.put(
				new PathHolder(entry.getKey()),
				new PathHolder(entry.getValue()));
		}
	}

	public long getId() {
		return _id;
	}

	public Map<Path, Path> getInputPathMap() {
		Map<Path, Path> inputPathMap = new HashMap<>();

		for (Map.Entry<PathHolder, PathHolder> entry :
				_inputPathHolderMap.entrySet()) {

			PathHolder keyPathHolder = entry.getKey();
			PathHolder valuePathHolder = entry.getValue();

			inputPathMap.put(
				keyPathHolder.getPath(), valuePathHolder.getPath());
		}

		return inputPathMap;
	}

	public ProcessCallable<T> getProcessCallable() {
		return _processCallable;
	}

	public ProcessConfig getProcessConfig() {
		return _processConfig;
	}

	private static final long serialVersionUID = 1L;

	private final long _id;
	private final Map<PathHolder, PathHolder> _inputPathHolderMap;
	private final ProcessCallable<T> _processCallable;
	private final ProcessConfig _processConfig;

	private static class NettyFabricWorkerProcessCallable
		<T extends Serializable>
			implements ProcessCallable<T> {

		public NettyFabricWorkerProcessCallable(
			ProcessCallable<T> processCallable) {

			_data = SerializableUtil.serialize(processCallable);
			_toString = processCallable.toString();
		}

		@Override
		public T call() throws ProcessException {
			ProcessCallable<T> processCallable =
				(ProcessCallable<T>)SerializableUtil.deserialize(_data);

			return processCallable.call();
		}

		@Override
		public String toString() {
			return _toString;
		}

		private static final long serialVersionUID = 1L;

		private final byte[] _data;
		private final String _toString;

	}

}
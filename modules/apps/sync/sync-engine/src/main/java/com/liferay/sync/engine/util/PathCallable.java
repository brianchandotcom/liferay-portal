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

package com.liferay.sync.engine.util;

import java.nio.file.Path;

import java.util.concurrent.Callable;

/**
 * @author Shinn Lok
 */
public abstract class PathCallable implements Callable {

	public PathCallable(Path path) {
		_path = path;
	}

	public Path getPath() {
		return _path;
	}

	public long getStartTime() {
		return _START_TIME;
	}

	private static final long _START_TIME = System.currentTimeMillis();

	private final Path _path;

}
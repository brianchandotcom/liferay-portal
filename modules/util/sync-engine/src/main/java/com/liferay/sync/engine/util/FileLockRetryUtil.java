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

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Shinn Lok
 */
public class FileLockRetryUtil {

	public static void init() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				Iterator<PathCallable> iterator = _pathCallables.iterator();

				while (iterator.hasNext()) {
					if (processPathCallable(iterator.next())) {
						iterator.remove();
					}
				}
			}

		};

		_scheduledExecutorService.scheduleWithFixedDelay(
			runnable, 0, 5, TimeUnit.SECONDS);
	}

	public static boolean processPathCallable(PathCallable pathCallable) {
		FileChannel fileChannel = null;

		FileLock fileLock = null;

		try {
			Path filePath = pathCallable.getPath();

			if (Files.notExists(filePath)) {
				return true;
			}

			if (Files.isDirectory(filePath)) {
				pathCallable.call();

				return true;
			}
			else {
				fileChannel = FileChannel.open(
					filePath, StandardOpenOption.READ,
					StandardOpenOption.WRITE);

				fileLock = FileUtil.getFileLock(fileChannel);
			}

			if (fileLock != null) {
				pathCallable.call();

				return true;
			}
		}
		catch (Exception e) {
		}
		finally {
			FileUtil.releaseFileLock(fileLock);

			StreamUtil.cleanUp(fileChannel);
		}

		return false;
	}

	public static void registerPathCallable(PathCallable pathCallable) {
		if (!processPathCallable(pathCallable)) {
			_pathCallables.add(pathCallable);
		}
	}

	public static void shutdown() {
		_scheduledExecutorService.shutdownNow();
	}

	private static final List<PathCallable> _pathCallables = new ArrayList<>();
	private static final ScheduledExecutorService _scheduledExecutorService =
		Executors.newSingleThreadScheduledExecutor();

}
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

package com.liferay.saml.opensaml.integration.internal.credential;

import java.io.Closeable;
import java.io.IOException;

import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Carlos Sierra Andrés
 */
public class FileWatcher implements Closeable {

	public FileWatcher(
			Consumer<WatchEvent<Path>> consumer,
			ExecutorService notificationsExecutorService,
			long notificationTimeout, TimeUnit notificationTimeUnit,
			Path... paths)
		throws IOException {

		this(
			consumer, Executors.newScheduledThreadPool(1), 1, 1,
			TimeUnit.SECONDS, notificationsExecutorService, notificationTimeout,
			notificationTimeUnit, paths);
	}

	public FileWatcher(Consumer<WatchEvent<Path>> consumer, Path... paths)
		throws IOException {

		this(
			consumer, Executors.newScheduledThreadPool(1), 1, 1,
			TimeUnit.SECONDS, Executors.newSingleThreadExecutor(), 10,
			TimeUnit.SECONDS, paths);
	}

	public FileWatcher(
			Consumer<WatchEvent<Path>> consumer,
			ScheduledExecutorService scheduledExecutorService,
			long initialDelay, long period, TimeUnit units,
			ExecutorService notificationsExecutorService,
			long notificationTimeout, TimeUnit notificationTimeUnit,
			Path... paths)
		throws IOException {

		_consumer = consumer;
		_scheduledExecutorService = scheduledExecutorService;

		_paths = Arrays.asList(paths);

		FileSystem fileSystem = FileSystems.getDefault();

		_watchService = fileSystem.newWatchService();

		for (Path path : _paths) {
			if (!Files.isDirectory(path)) {
				path = path.getParent();
			}

			path.register(
				_watchService, StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_MODIFY);
		}

		_scheduledExecutorService.scheduleAtFixedRate(
			() -> {
				WatchKey watchKey = null;

				try {
					watchKey = _watchService.take();
				}
				catch (ClosedWatchServiceException | InterruptedException e) {
					return;
				}

				if ((watchKey == null) || !watchKey.isValid()) {
					return;
				}

				List<WatchEvent<?>> watchEvents = watchKey.pollEvents();

				Stream<Path> pathsStream = _paths.stream();
				Stream<WatchEvent<?>> watchEventsStream = watchEvents.stream();

				Stream<WatchEvent<Path>> watchEventsPathStream =
					pathsStream.flatMap(
						path -> watchEventsStream.map(
							watchEvent -> (WatchEvent<Path>)watchEvent
						).filter(
							watchEvent -> {
								Path contextPath = watchEvent.context();

								return contextPath.endsWith(path.getFileName());
							}
						));

				CompletableFuture[] completableFutures =
					watchEventsPathStream.map(
						watchEvent -> {
							return (Runnable)() -> _consumer.accept(watchEvent);
						}
					).map(
						runnable -> CompletableFuture.runAsync(
							runnable, notificationsExecutorService)
					).toArray(
						CompletableFuture[]::new
					);

				CompletableFuture<Void> completableFuture =
					CompletableFuture.allOf(completableFutures);

				try {
					completableFuture.get(
						notificationTimeout, notificationTimeUnit);
				}
				catch (ExecutionException | InterruptedException |
					   TimeoutException e) {

					return;
				}

				watchKey.reset();
			},
			initialDelay, period, units);
	}

	@Override
	public void close() {
		try {
			_watchService.close();
		}
		catch (IOException ioe) {
		}

		_scheduledExecutorService.shutdownNow();
	}

	private final Consumer<WatchEvent<Path>> _consumer;
	private final List<Path> _paths;
	private final ScheduledExecutorService _scheduledExecutorService;
	private final WatchService _watchService;

}
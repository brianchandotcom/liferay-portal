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

package com.liferay.ant.watch;

import java.io.IOException;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author David Truong
 */
public class WatchTask extends Task {

	public void addWhen(WatchedTarget watchedTarget) {
		if (_watchedTarget == null) {
			_watchedTarget = new ArrayList<>();
		}

		_watchedTarget.add(watchedTarget);
	}

	@Override
	public void execute() throws BuildException {
		final ShutdownTask shutdown = new ShutdownTask(Thread.currentThread());

		try {
			_watcher = FileSystems.getDefault().newWatchService();

			for (WatchedTarget watchedTarget : _watchedTarget) {
				watchedTarget.startWatching(getProject(), _watcher);
			}

			while (true) {
				WatchKey key = _watcher.take();

				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();

					Path path = (Path)event.context();

					path = Paths.get(key.watchable().toString()).resolve(path);

					if (!Files.isDirectory(path)) {
						path = path.getParent();
					}

					if (kind == StandardWatchEventKinds.OVERFLOW) {
						continue;
					}

					for (WatchedTarget watchedTarget : _watchedTarget) {
						if (watchedTarget.watching(path)) {
							if (event.kind() ==
									StandardWatchEventKinds.ENTRY_CREATE) {

								log("Start watching: " + path);
								watchedTarget.addWatch(path, _watcher);
							}
							else if (event.kind() ==
										StandardWatchEventKinds.ENTRY_DELETE) {

								log("Stop watching: " + path);
								watchedTarget.removeWatch(path);
							}

							watchedTarget.execute(
								getProject(), (WatchEvent<Path>)event);
						}
					}
				}

				if (!key.reset()) {
					log(key.toString() + " could not be reset!");
				}
			}
		}
		catch (IOException e) {
			throw new BuildException("IO Exception", e);
		}
		catch (InterruptedException e) {

			// todo shutting down

		}
		finally {
			for (WatchedTarget watch : _watchedTarget) {
				watch.stopWatching();
			}

			synchronized (shutdown) {
				shutdown.notifyAll();
			}
		}
	}

	private List<WatchedTarget> _watchedTarget;
	private WatchService _watcher;

	private class ShutdownTask implements Runnable {

		public void run() {
			try {
				log("Shut down received...");
				_antThread.interrupt();
				synchronized ( this ) {
					this.wait();
				}

				log("done");
			}
			catch (InterruptedException e) {
				log("Shutting down because interrupted");
			}
		}

		private ShutdownTask(Thread antThread) {
			_antThread = antThread;
			_shutdownThread = new Thread(this);
			Runtime.getRuntime().addShutdownHook(_shutdownThread);
		}

		private final Thread _antThread;
		private final Thread _shutdownThread;

	}

}
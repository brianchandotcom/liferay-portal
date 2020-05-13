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

package com.liferay.gradle.util.tasks;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.work.ExecuteJavaWorkAction;
import com.liferay.gradle.util.work.ExecuteJavaWorkParameters;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.jvm.Jvm;
import org.gradle.util.GUtil;
import org.gradle.workers.ClassLoaderWorkerSpec;
import org.gradle.workers.ProcessWorkerSpec;
import org.gradle.workers.WorkQueue;
import org.gradle.workers.WorkerExecutor;

/**
 * @author Peter Shin
 */
public abstract class ExecuteJavaTask extends DefaultTask {

	@Inject
	public ExecuteJavaTask(WorkerExecutor workerExecutor) {
		_workerExecutor = workerExecutor;
	}

	@TaskAction
	public void executeJava() {
		_submitWorkQueue(_createWorkQueue());
	}

	public FileCollection getClasspath() {
		return null;
	}

	public List<String> getJvmArgs() {
		return GradleUtil.toStringList(_jvmArgs);
	}

	public boolean isFork() {
		return _fork;
	}

	@SuppressWarnings("unchecked")
	public ExecuteJavaTask jvmArgs(Iterable<Object> jvmArgs) {
		GUtil.addToCollection(_jvmArgs, jvmArgs);

		return this;
	}

	public ExecuteJavaTask jvmArgs(Object... jvmArgs) {
		return jvmArgs(Arrays.asList(jvmArgs));
	}

	public void setFork(boolean fork) {
		_fork = fork;
	}

	public void setJvmArgs(Iterable<Object> jvmArgs) {
		_jvmArgs.clear();

		jvmArgs(jvmArgs);
	}

	public void setJvmArgs(Object... jvmArgs) {
		setJvmArgs(Arrays.asList(jvmArgs));
	}

	protected List<String> getArgs() {
		return null;
	}

	protected abstract String getMain();

	protected WorkerExecutor getWorkerExecutor() {
		return _workerExecutor;
	}

	private WorkQueue _createWorkQueue() {
		final FileCollection classpath = getClasspath();
		final List<String> jvmArgs = getJvmArgs();
		final Logger logger = getLogger();

		WorkerExecutor workerExecutor = getWorkerExecutor();

		if (isFork() || ((jvmArgs != null) && !jvmArgs.isEmpty())) {
			return workerExecutor.processIsolation(
				new Action<ProcessWorkerSpec>() {

					@Override
					public void execute(ProcessWorkerSpec processWorkerSpec) {
						processWorkerSpec.forkOptions(
							forkOptions -> {
								forkOptions.jvmArgs(jvmArgs);

								Jvm jvm = Jvm.current();

								forkOptions.setEnvironment(
									jvm.getInheritableEnvironmentVariables(
										System.getenv()));
							});

						if ((classpath != null) && !classpath.isEmpty()) {
							ConfigurableFileCollection processWorkerClasspath =
								processWorkerSpec.getClasspath();

							processWorkerClasspath.from(classpath);
						}

						if (logger.isInfoEnabled()) {
							logger.info("Running in process isolation");

							if ((jvmArgs != null) && !jvmArgs.isEmpty()) {
								logger.info("JVM arguments: {}", jvmArgs);
							}

							if ((classpath != null) && !classpath.isEmpty()) {
								logger.info(
									"Classpath: {}", classpath.getAsPath());
							}
						}
					}

				});
		}

		if ((classpath != null) && !classpath.isEmpty()) {
			return workerExecutor.classLoaderIsolation(
				new Action<ClassLoaderWorkerSpec>() {

					@Override
					public void execute(
						ClassLoaderWorkerSpec classLoaderWorkerSpec) {

						ConfigurableFileCollection classLoaderWorkerClasspath =
							classLoaderWorkerSpec.getClasspath();

						classLoaderWorkerClasspath.from(classpath);

						if (logger.isInfoEnabled()) {
							logger.info(
								"Running in class loader isolation: {}",
								classpath.getAsPath());
						}
					}

				});
		}

		if (logger.isInfoEnabled()) {
			logger.info("Running with no isolation");
		}

		return workerExecutor.noIsolation();
	}

	private void _submitWorkQueue(WorkQueue workQueue) {
		workQueue.submit(
			ExecuteJavaWorkAction.class,
			new Action<ExecuteJavaWorkParameters>() {

				@Override
				public void execute(
					ExecuteJavaWorkParameters executeJavaWorkParameters) {

					ListProperty<String> argsListProperty =
						executeJavaWorkParameters.getArgs();

					List<String> args = getArgs();

					if (args == null) {
						args = Collections.emptyList();
					}

					argsListProperty.set(args);

					Property<String> mainProperty =
						executeJavaWorkParameters.getMain();

					String main = getMain();

					mainProperty.set(main);

					Logger logger = getLogger();

					if (logger.isInfoEnabled()) {
						logger.info("Running {}: {}", main, args);
					}
				}

			});
	}

	private boolean _fork;
	private final Set<Object> _jvmArgs = new HashSet<>();
	private final WorkerExecutor _workerExecutor;

}
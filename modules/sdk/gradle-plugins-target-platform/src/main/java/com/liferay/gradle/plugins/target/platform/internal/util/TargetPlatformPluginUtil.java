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

package com.liferay.gradle.plugins.target.platform.internal.util;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementConfigurer;
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension;
import io.spring.gradle.dependencymanagement.dsl.ImportsHandler;

import java.util.ArrayList;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;

/**
 * @author Gregory Amerson
 */
public class TargetPlatformPluginUtil {

	public static void configureDependencyManagement(
		final Project project,
		final Configuration targetPlatformBomsConfiguration,
		Iterable<?> configurationNames) {

		final DependencyManagementExtension dependencyManagementExtension =
			GradleUtil.getExtension(
				project, DependencyManagementExtension.class);

		dependencyManagementExtension.setApplyMavenExclusions(false);

		GroovyObjectSupport groovyObjectSupport =
			(GroovyObjectSupport)dependencyManagementExtension;

		List<Object> args = new ArrayList<>();

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		for (Object configurationName : configurationNames) {
			Configuration configuration = null;

			if (configurationName instanceof Configuration) {
				configuration = (Configuration)configurationName;
			}
			else {
				configuration = configurationContainer.findByName(
					GradleUtil.toString(configurationName));
			}

			if (configuration != null) {
				args.add(configuration);
			}
		}

		Closure<Void> closure = new Closure<Void>(project) {

			@SuppressWarnings("unused")
			public void doCall() {
				DependencySet dependencySet =
					targetPlatformBomsConfiguration.getAllDependencies();

				dependencySet.all(
					new Action<Dependency>() {

						@Override
						public void execute(final Dependency dependency) {
							_configureDependencyManagementImportsHandler(
								(DependencyManagementConfigurer)getDelegate(),
								dependency);
						}

					});
			}

		};

		args.add(closure);

		groovyObjectSupport.invokeMethod(
			"configurations", args.toArray(new Object[0]));
	}

	private static void _configureDependencyManagementImportsHandler(
		DependencyManagementConfigurer dependencyManagementConfigurer,
		final Dependency dependency) {

		dependencyManagementConfigurer.imports(
			new Action<ImportsHandler>() {

				@Override
				public void execute(ImportsHandler importsHandler) {
					StringBuilder sb = new StringBuilder();

					sb.append(dependency.getGroup());
					sb.append(':');
					sb.append(dependency.getName());
					sb.append(':');
					sb.append(dependency.getVersion());

					importsHandler.mavenBom(sb.toString());
				}

			});
	}

}
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

package com.liferay.gradle.plugins.workspace.internal.client.extension;

import groovy.lang.Closure;

import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Zip;

/**
 * @author Gregory Amerson
 */
public class ThemeFaviconTypeConfigurer
	implements ClientExtensionTypeConfigurer {

	@Override
	@SuppressWarnings("serial")
	public void apply(
		Project project, ClientExtension clientExtension,
		TaskProvider<Zip> zipTaskProvider) {

		zipTaskProvider.configure(
			zip -> zip.into(
				new Callable<String>() {

					@Override
					public String call() throws Exception {
						return "static";
					}

				},
				new Closure<Void>(zip) {

					@SuppressWarnings("unused")
					public void doCall(CopySpec copySpec) {
						copySpec.from(project.file("src"));
						copySpec.include("*.ico");
						copySpec.setIncludeEmptyDirs(false);
					}

				}));
	}

}
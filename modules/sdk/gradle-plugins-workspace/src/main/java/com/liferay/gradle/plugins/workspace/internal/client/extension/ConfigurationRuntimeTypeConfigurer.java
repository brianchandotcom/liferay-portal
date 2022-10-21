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

import java.util.Optional;

import org.gradle.api.Project;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.TaskProvider;

/**
 * @author Gregory Amerson
 */
public class ConfigurationRuntimeTypeConfigurer
	implements ClientExtensionTypeConfigurer {

	@Override
	public void apply(
		Project project, Optional<ClientExtension> clientExtensionOptional,
		TaskProvider<Copy> assembleClientExtensionTaskProvider) {

		assembleClientExtensionTaskProvider.configure(
			copy -> copy.from("src", copySpec -> copySpec.include("**/*")));
	}

}
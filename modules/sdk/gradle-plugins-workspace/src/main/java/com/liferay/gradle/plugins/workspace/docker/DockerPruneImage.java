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

package com.liferay.gradle.plugins.workspace.docker;

import com.bmuschko.gradle.docker.tasks.image.DockerExistingImage;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.PruneCmd;
import com.github.dockerjava.api.command.RemoveImageCmd;
import com.github.dockerjava.api.model.PruneType;

import groovy.transform.CompileStatic;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Optional;

/**
 * @author Seiphon Wang
 */
@CompileStatic
public class DockerPruneImage extends DockerExistingImage {

	public DockerPruneImage() {
		Project project = getProject();

		ObjectFactory objects = project.getObjects();

		_force = objects.property(Boolean.class);
	}

	@Input
	@Optional
	public final Property<Boolean> getForce() {
		return _force;
	}

	@Override
	public void runRemoteCommand() {
		Logger logger = getLogger();

		Property<String> imageId = getImageId();

		logger.quiet("Removing image with ID \'" + imageId.get() + "\'.");

		DockerClient dockerClient = getDockerClient();

		dockerClient.removeImageCmd(imageId.get());

		RemoveImageCmd removeImageCmd = dockerClient.removeImageCmd(
			imageId.get());

		if (Boolean.TRUE.equals(_force.getOrNull())) {
			removeImageCmd.withForce(_force.get());
		}

		removeImageCmd.exec();

		PruneCmd pruneCmd = dockerClient.pruneCmd(PruneType.IMAGES);

		pruneCmd.exec();
	}

	private final Property<Boolean> _force;

}
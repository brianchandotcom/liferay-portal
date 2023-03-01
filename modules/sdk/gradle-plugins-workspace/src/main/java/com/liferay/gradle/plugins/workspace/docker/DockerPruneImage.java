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
import com.github.dockerjava.api.model.PruneType;

import org.gradle.api.logging.Logger;
import org.gradle.api.provider.Property;

/**
 * @author Seiphon Wang
 */
public class DockerPruneImage extends DockerExistingImage {

	@Override
	public void runRemoteCommand() {
		Logger logger = getLogger();

		if (logger.isQuietEnabled()) {
			Property<String> imageIdProperty = getImageId();

			logger.quiet(
				"Pruning image with ID \'" + imageIdProperty.get() + "\'.");
		}

		DockerClient dockerClient = getDockerClient();

		PruneCmd pruneCmd = dockerClient.pruneCmd(PruneType.IMAGES);

		pruneCmd = pruneCmd.withDangling(true);

		pruneCmd.exec();
	}

}
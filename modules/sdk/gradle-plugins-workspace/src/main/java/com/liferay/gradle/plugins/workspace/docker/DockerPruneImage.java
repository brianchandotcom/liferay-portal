/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
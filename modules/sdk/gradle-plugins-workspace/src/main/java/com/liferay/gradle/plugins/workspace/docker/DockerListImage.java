/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.docker;

import com.bmuschko.gradle.docker.tasks.AbstractDockerRemoteApiTask;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListImagesCmd;
import com.github.dockerjava.api.model.Image;

import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

/**
 * @author Seiphon Wang
 */
public class DockerListImage extends AbstractDockerRemoteApiTask {

	public DockerListImage() {
		Project project = getProject();

		ObjectFactory objectFactory = project.getObjects();

		_imageIdProperty = objectFactory.property(String.class);
		_imagesProperty = objectFactory.listProperty(Image.class);
		_withDanglingFilter = objectFactory.property(Boolean.class);

		Action<Image> action = new Action<Image>() {

			@Override
			public void execute(Image image) {
				_imageIdProperty.set(image.getId());
				_imagesProperty.add(image);
			}

		};

		onNext(action);
	}

	@Internal
	public String getImageId() {
		return _imageIdProperty.get();
	}

	@Input
	public List<Image> getImages() {
		return _imagesProperty.get();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void runRemoteCommand() {
		DockerClient dockerClient = getDockerClient();

		ListImagesCmd listImagesCmd = dockerClient.listImagesCmd();

		listImagesCmd.withDanglingFilter(_withDanglingFilter.getOrElse(true));

		if (getNextHandler() != null) {
			List<Image> images = listImagesCmd.exec();

			for (Image image : images) {
				Action<Image> action = getNextHandler();

				action.execute(image);
			}
		}
	}

	public boolean withDanglingFilter() {
		return _withDanglingFilter.get();
	}

	private final Property<String> _imageIdProperty;
	private final ListProperty<Image> _imagesProperty;
	private final Property<Boolean> _withDanglingFilter;

}
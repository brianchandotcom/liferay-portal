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

	@Input
	private Property<String> _imageIdProperty;

	@Input
	private ListProperty<Image> _imagesProperty;

	@Input
	private Property<Boolean> _withDanglingFilter;

}
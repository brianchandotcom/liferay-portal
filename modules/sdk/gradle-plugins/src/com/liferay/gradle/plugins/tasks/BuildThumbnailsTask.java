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

package com.liferay.gradle.plugins.tasks;

import com.liferay.gradle.util.FileUtil;

import java.io.File;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.SkipWhenEmpty;
import org.gradle.process.internal.streams.SafeStreams;

/**
 * @author Andrea Di Giorgi
 */
public class BuildThumbnailsTask extends BasePortalToolsTask {

	@Override
	public void exec() {
		Iterable<File> files = getScreenshotFiles();

		for (File file : files) {
			super.setErrorOutput(SafeStreams.systemErr());
			super.setStandardOutput(SafeStreams.systemOut());

			doExec(getArgs(file));
		}
	}

	@Override
	public List<String> getArgs() {
		List<String> args = new ArrayList<>();

		args.add("thumbnail.height=" + getHeight());
		args.add("thumbnail.width=" + getWidth());
		args.add("thumbnail.overwrite=" + isOverwrite());

		return args;
	}

	@Input
	public int getHeight() {
		return _height;
	}

	public File getImagesDir() {
		return _imagesDir;
	}

	@Override
	public String getMain() {
		return "com.liferay.portal.tools.ThumbnailBuilder";
	}

	@InputFiles
	@SkipWhenEmpty
	public Iterable<File> getScreenshotFiles() {
		File imagesDir = getImagesDir();

		if (imagesDir == null) {
			return Collections.emptyList();
		}

		Map<String, Object> args = new HashMap<>();

		args.put("dir", imagesDir);
		args.put("include", "**/screenshot.png");

		return project.fileTree(args);
	}

	@Input
	public int getWidth() {
		return _width;
	}

	@Input
	public boolean isOverwrite() {
		return _overwrite;
	}

	@Override
	public JavaExec setErrorOutput(OutputStream outputStream) {
		throw new UnsupportedOperationException();
	}

	public void setHeight(int height) {
		_height = height;
	}

	public void setImagesDir(File imagesDir) {
		_imagesDir = imagesDir;
	}

	public void setOverwrite(boolean overwrite) {
		_overwrite = overwrite;
	}

	@Override
	public JavaExec setStandardOutput(OutputStream outputStream) {
		throw new UnsupportedOperationException();
	}

	public void setWidth(int width) {
		_width = width;
	}

	@Override
	protected void addDependencies() {
		super.addDependencies();

		addDependency("com.liferay", "org.monte", "0.7.7");
	}

	protected List<String> getArgs(File screenshotFile) {
		List<String> args = getArgs();

		args.add(
			"thumbnail.original.file=" +
				FileUtil.getAbsolutePath(screenshotFile));

		File thumbnailFile = new File(
			screenshotFile.getParentFile(), "thumbnail.png");

		args.add(
			"thumbnail.thumbnail.file=" +
				FileUtil.getAbsolutePath(thumbnailFile));

		return args;
	}

	@Override
	protected String getToolName() {
		return "ThumbnailBuilder";
	}

	private int _height = 120;
	private File _imagesDir;
	private boolean _overwrite;
	private int _width = 160;

}
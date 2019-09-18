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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.plugins.node.internal.util.DigestUtil;
import com.liferay.gradle.plugins.node.internal.util.FileUtil;
import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.io.File;

import java.nio.charset.StandardCharsets;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTree;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputFile;
import org.gradle.api.tasks.util.PatternFilterable;
import org.gradle.api.tasks.util.PatternSet;

/**
 * @author Peter Shin
 */
public class PackageRunBuildTask extends PackageRunTask {

	public PackageRunBuildTask() {
		setScriptName("build");

		_patternFilterable.exclude(_EXCLUDE_DIR_NAMES);
		_patternFilterable.include(_INCLUDES);
	}

	@Override
	public void executeNode() throws Exception {
		String digest = DigestUtil.getDigest(getSourceFiles());

		super.executeNode();

		File digestFile = getDigestFile();

		FileUtil.write(digestFile, digest.getBytes(StandardCharsets.UTF_8));
	}

	@OutputFile
	public File getDigestFile() {
		Project project = getProject();

		return new File(
			project.getBuildDir(), "node/" + getName() + "/.digest");
	}

	@Input
	public String getNodeVersion() {
		return GradleUtil.toString(_nodeVersion);
	}

	@InputFiles
	public FileCollection getSourceFiles() {
		Project project = getProject();

		FileTree fileTree = project.fileTree(project.getProjectDir());

		FileCollection fileCollection = fileTree.matching(_patternFilterable);

		if (fileCollection.isEmpty()) {
			return null;
		}

		return fileCollection;
	}

	public void setNodeVersion(Object nodeVersion) {
		_nodeVersion = nodeVersion;
	}

	private static final String[] _EXCLUDE_DIR_NAMES = {
		"bin", "build", "classes", "node_modules", "node_modules_cache",
		"test-classes", "tmp"
	};

	private static final String[] _INCLUDES = {
		"**/*.*rc", "**/*.css", "**/*.js", "**/*.json", "**/*.jsx", "**/*.soy"
	};

	private Object _nodeVersion;
	private final PatternFilterable _patternFilterable = new PatternSet();

}
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

import com.liferay.gradle.plugins.node.internal.util.GradleUtil;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;

/**
 * @author David Truong
 */
public class NpmRunTask extends ExecuteNpmTask {

	public NpmRunTask() {
		Project project = getProject();

		_reportFile = new File(getTemporaryDir(), "report.txt");
		_sourceDir = project.file("src");
	}

	@Override
	public void executeNode() throws Exception {
		super.executeNode();

		StringBuilder sb = new StringBuilder();

		sb.append("Executed npmRun");
		sb.append(_scriptName);

		File nodeModulesDir = getNodeModulesDir();

		sb.append("\n\n=====Node Modules=====\n");
		sb.append("Path: ");
		sb.append(nodeModulesDir.getAbsolutePath());

		for (File file : nodeModulesDir.listFiles()) {
			String fileName = file.getName();

			if (fileName.startsWith(".")) {
				continue;
			}

			sb.append(fileName);
			sb.append("\n");
		}

		File sourceDir = getSourceDir();

		sb.append("\n\n=====Source Files=====\n");
		sb.append("Path: ");
		sb.append(sourceDir.getAbsolutePath());

		try {
			Files.walkFileTree(
				sourceDir.toPath(),
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path file, BasicFileAttributes attrs)
						throws IOException {

						if (!attrs.isDirectory()) {
							sb.append(file.getFileName());
							sb.append("\n");
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioe) {
			Logger logger = getLogger();

			logger.warn("Could not generate source files");
		}

		String report = sb.toString();

		File reportFile = getReportFile();

		Path path = reportFile.toPath();

		Files.createDirectories(path.getParent());

		Files.write(path, report.getBytes(StandardCharsets.UTF_8));
	}

	@InputDirectory
	public File getNodeModulesDir() {
		Project project = getProject();

		return project.file("node_modules");
	}

	@InputFile
	public File getPackageJsonFile() {
		Project project = getProject();

		return project.file("package.json");
	}

	@OutputFile
	public File getReportFile() {
		return GradleUtil.toFile(getProject(), _reportFile);
	}

	@Input
	public String getScriptName() {
		return _scriptName;
	}

	@InputDirectory
	public File getSourceDir() {
		return GradleUtil.toFile(getProject(), _sourceDir);
	}

	public void setScriptName(String scriptName) {
		_scriptName = scriptName;
	}

	public void setSourceDir(Object sourceDir) {
		_sourceDir = sourceDir;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("run-script");
		completeArgs.add(_scriptName);

		return completeArgs;
	}

	private final Object _reportFile;
	private String _scriptName;
	private Object _sourceDir;

}
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

package com.liferay.arquillian.extension.junit.bridge.bnd;

import aQute.bnd.build.Project;
import aQute.bnd.build.ProjectBuilder;
import aQute.bnd.build.Workspace;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

import com.liferay.arquillian.extension.junit.bridge.activator.ArquillianBundleActivator;
import com.liferay.arquillian.extension.junit.bridge.statement.DeploymentStatement;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.io.File;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class BndBundleUtil {

	public static Path createBundle() {
		try (Workspace workspace = new Workspace(_buildDir);
			Project project = new Project(workspace, _buildDir);

			ProjectBuilder projectBuilder = _createProjectBuilder(project);

			Jar jar = projectBuilder.build();
			Analyzer analyzer = new Analyzer()) {

			analyzer.setProperties(project.getProperties());
			analyzer.setJar(jar);

			jar.setManifest(analyzer.calcManifest());

			Path path = Files.createTempFile(null, ".jar");

			jar.write(path.toFile());

			return path;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static ProjectBuilder _createProjectBuilder(Project project)
		throws Exception {

		project.setProperty(
			"Bundle-Activator",
			ArquillianBundleActivator.class.getCanonicalName());

		Set<String> importPackages = new HashSet<>();

		importPackages.add("!aQute.bnd.build");
		importPackages.add("!aQute.bnd.osgi");
		importPackages.add("*");

		String importPackageString = project.getProperty("Import-Package");

		if (importPackageString != null) {
			importPackages.addAll(StringUtil.split(importPackageString));
		}

		project.setProperty(
			"Import-Package",
			StringUtil.merge(importPackages, StringPool.COMMA));

		Set<String> includeResources = new HashSet<>();

		includeResources.add("arquillian.remote.marker;literal=''");
		includeResources.add("test-classes/integration");

		ProtectionDomain protectionDomain =
			DeploymentStatement.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		File file = new File(url.toURI());

		includeResources.add("@" + file.getName() + "!/*");

		String includeResourceString = project.getProperty("-includeresource");

		if (includeResourceString != null) {
			includeResources.addAll(StringUtil.split(includeResourceString));
		}

		project.setProperty(
			"-includeresource",
			StringUtil.merge(includeResources, StringPool.COMMA));

		ProjectBuilder projectBuilder = new ProjectBuilder(project);

		projectBuilder.addClasspath(_getClassPathFiles());

		return projectBuilder;
	}

	private static List<File> _getClassPathFiles() {
		List<File> files = new ArrayList<>();

		List<String> fileNames = StringUtil.split(
			System.getProperty("java.class.path"), File.pathSeparatorChar);

		for (String fileName : fileNames) {
			File file = new File(fileName);

			int length = fileName.length();

			if (file.isDirectory() ||
				fileName.regionMatches(true, length - 4, ".zip", 0, 4) ||
				fileName.regionMatches(true, length - 4, ".jar", 0, 4)) {

				files.add(file);
			}
		}

		return files;
	}

	private static final File _buildDir = new File(
		System.getProperty("user.dir"));

}
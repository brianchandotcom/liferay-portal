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

package com.liferay.arquillian.extension.junit.bridge.deployment;

import aQute.bnd.build.Project;
import aQute.bnd.build.ProjectBuilder;
import aQute.bnd.build.Workspace;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

import com.liferay.portal.kernel.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Preston Crary
 */
public class BndDeploymentScenarioGenerator
	implements DeploymentScenarioGenerator {

	@Override
	public List<DeploymentDescription> generate(TestClass testClass) {
		try (Analyzer analyzer = new Analyzer()) {
			Workspace workspace = new Workspace(_buildDir);

			Project project = new Project(workspace, _buildDir);

			ProjectBuilder projectBuilder = new ProjectBuilder(project);

			projectBuilder.addClasspath(_getClassPathFiles());

			Jar jar = projectBuilder.build();

			analyzer.setProperties(project.getProperties());
			analyzer.setJar(jar);

			jar.setManifest(analyzer.calcManifest());

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			jar.write(byteArrayOutputStream);

			ZipImporter zipImporter = ShrinkWrap.create(ZipImporter.class);

			zipImporter.importFrom(
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

			JavaArchive javaArchive = zipImporter.as(JavaArchive.class);

			return Collections.singletonList(
				new DeploymentDescription(javaArchive.getName(), javaArchive));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<File> _getClassPathFiles() {
		List<File> files = new ArrayList<>();

		for (String filePath : StringUtil.split(
				System.getProperty("java.class.path"),
				File.pathSeparatorChar)) {

			File file = new File(filePath);

			if (file.isDirectory() || StringUtil.endsWith(filePath, ".zip") ||
				StringUtil.endsWith(filePath, ".jar")) {

				files.add(file);
			}
		}

		return files;
	}

	private static final File _buildDir = new File(
		System.getProperty("user.dir"));

}
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

package com.liferay.arquillian.deployment;

import aQute.bnd.build.Project;
import aQute.bnd.build.ProjectBuilder;
import aQute.bnd.build.Workspace;
import aQute.bnd.osgi.Jar;

import com.liferay.arquillian.junit.Arquillian;
import com.liferay.arquillian.util.PropsUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Shuyang Zhou
 */
public class ModuleDeploymentScenarioGenerator
	implements DeploymentScenarioGenerator {

	@Override
	public List<DeploymentDescription> generate(TestClass testClass) {
		File workingFolder = new File(System.getProperty("user.dir"));

		try {
			Project project = new Project(
				new Workspace(workingFolder), workingFolder);

			Properties properties = project.getProperties();

			try (InputStream inputStream = new FileInputStream(
					new File(System.getProperty("sdk.dir"), "common.bnd"))) {

				properties.load(inputStream);
			}

			try (InputStream inputStream = new FileInputStream(
					new File("bnd.bnd"))) {

				properties.load(inputStream);
			}

			ProjectBuilder projectBuilder = new ProjectBuilder(project);

			try (Jar jar = projectBuilder.build()) {
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
					new UnsyncByteArrayOutputStream();

				jar.write(unsyncByteArrayOutputStream);

				ZipImporter zipImporter = ShrinkWrap.create(ZipImporter.class);

				zipImporter.importFrom(
					new UnsyncByteArrayInputStream(
						unsyncByteArrayOutputStream.unsafeGetByteArray(), 0,
						unsyncByteArrayOutputStream.size()));

				JavaArchive javaArchive = zipImporter.as(JavaArchive.class);

				javaArchive.addClass(Arquillian.class);

				if (GetterUtil.getBoolean(
						PropsUtil.get("dump.generated.archives"))) {

					ZipExporter zipExporter = javaArchive.as(ZipExporter.class);

					File file = new File(
						ModuleDeploymentScenarioGenerator.class.getName() +
							"-" + javaArchive.getName());

					zipExporter.exportTo(file);

					System.out.println(
						"Dumpped file " + file.getAbsolutePath());
				}

				return Collections.singletonList(
					new DeploymentDescription(
						javaArchive.getName(), javaArchive));
			}
		}
		catch (Exception e) {
			throw new RuntimeException(
				"Unable to generate test module bundle", e);
		}
	}

}
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

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.shrinkwrap.osgi.api.BndProjectBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.jar.Attributes.Name;
import java.util.jar.Manifest;

import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Preston Crary
 */
public class BndDeploymentScenarioGenerator
	implements DeploymentScenarioGenerator {

	@Override
	public List<DeploymentDescription> generate(TestClass testClass) {
		File bndFile = new File("bnd.bnd");

		List<DeploymentDescription> deployments = new ArrayList<>();

		try (Analyzer analyzer = new Analyzer()) {
			BndProjectBuilder bndProjectBuilder = ShrinkWrap.create(
				BndProjectBuilder.class);

			bndProjectBuilder.setBndFile(bndFile);
			bndProjectBuilder.generateManifest(true);

			String javaClassPathString = System.getProperty("java.class.path");

			String[] javaClassPaths = javaClassPathString.split(
				File.pathSeparator);

			for (String javaClassPath : javaClassPaths) {
				File file = new File(javaClassPath);

				if (file.isDirectory() ||
					StringUtil.endsWith(javaClassPath, ".zip") ||
					StringUtil.endsWith(javaClassPath, ".jar")) {

					bndProjectBuilder.addClassPath(file);
				}
			}

			JavaArchive javaArchive = bndProjectBuilder.as(JavaArchive.class);

			Properties analyzerProperties = new Properties();

			analyzerProperties.putAll(analyzer.loadProperties(bndFile));

			analyzer.setProperties(analyzerProperties);

			javaArchive.addClass(testClass.getJavaClass());

			ZipExporter zipExporter = javaArchive.as(ZipExporter.class);

			Jar jar = new Jar(
				javaArchive.getName(), zipExporter.exportAsInputStream());

			analyzer.setJar(jar);

			DeploymentDescription deploymentDescription =
				new DeploymentDescription(javaArchive.getName(), javaArchive);

			deploymentDescription.shouldBeTestable(true);
			deploymentDescription.shouldBeManaged(true);

			deployments.add(deploymentDescription);

			Asset asset = javaArchive.get(_MANIFEST_PATH).getAsset();

			Manifest firstPassManifest = new Manifest(asset.openStream());

			firstPassManifest.getMainAttributes().remove(
				new Name("Import-Package"));

			analyzer.mergeManifest(firstPassManifest);

			Manifest manifest = analyzer.calcManifest();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			manifest.write(baos);

			ByteArrayAsset byteArrayAsset = new ByteArrayAsset(
				baos.toByteArray());

			javaArchive.delete(_MANIFEST_PATH);

			javaArchive.add(byteArrayAsset, _MANIFEST_PATH);

			return deployments;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final String _MANIFEST_PATH = "META-INF/MANIFEST.MF";

}
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
import com.liferay.arquillian.deploymentscenario.annotations.BndFile;
import com.liferay.shrinkwrap.osgi.api.BndProjectBuilder;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentDescription;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.impl.client.deployment.AnnotationDeploymentScenarioGenerator;
import org.jboss.arquillian.container.test.spi.client.deployment.DeploymentScenarioGenerator;
import org.jboss.arquillian.core.api.Injector;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * @author Preston Crary
 */
public class BndDeploymentScenarioGenerator
	implements DeploymentScenarioGenerator {

	public static final String MANIFEST_PATH = "META-INF/MANIFEST.MF";

	@Inject
	protected Instance<Injector> injector;

	private File bndFile = new File("bnd.bnd");
	private File commonBndFile;

	public BndDeploymentScenarioGenerator() {
		String sdkDir = System.getProperty("sdk.dir");

		if ((sdkDir != null) && !sdkDir.isEmpty()) {
			commonBndFile = new File(sdkDir, "common.bnd");
		}
	}

	@Override
	public List<DeploymentDescription> generate(TestClass testClass) {
		List<DeploymentDescription> deployments = new ArrayList<>();

		DeploymentScenarioGenerator defaultDeploymentScenarioGenerator =
			getDefaultDeploymentScenarioGenerator();

		if (defaultDeploymentScenarioGenerator != null) {
			List<DeploymentDescription> annotationDeployments =
				defaultDeploymentScenarioGenerator.generate(testClass);

			if ((annotationDeployments != null) &&
				!annotationDeployments.isEmpty()) {

				return annotationDeployments;
			}
		}

		try (Analyzer analyzer = new Analyzer()) {
			bndFile = getBndFile(testClass);

			BndProjectBuilder bndProjectBuilder = ShrinkWrap.create(
				BndProjectBuilder.class);

			bndProjectBuilder.setBndFile(bndFile);
			bndProjectBuilder.generateManifest(true);

			if (commonBndFile != null) {
				bndProjectBuilder.addProjectPropertiesFile(commonBndFile);
			}

			String javaClassPathString = System.getProperty(
				"java.class.path");
			String[] javaClassPaths = javaClassPathString.split(
				File.pathSeparator);

			for (String javaClassPath : javaClassPaths) {
				File file = new File(javaClassPath);

				if (file.isDirectory() ||
					javaClassPath.toLowerCase().endsWith(".zip") ||
					javaClassPath.toLowerCase().endsWith(".jar")) {

					bndProjectBuilder.addClassPath(file);
				}
			}

			JavaArchive javaArchive = bndProjectBuilder.as(
				JavaArchive.class);

			javaArchive.addClass(BndFile.class);

			Properties analyzerProperties = new Properties();

			if (commonBndFile != null) {
				analyzerProperties.putAll(
					analyzer.loadProperties(commonBndFile));
			}

			analyzerProperties.putAll(analyzer.loadProperties(bndFile));

			analyzer.setProperties(analyzerProperties);

			boolean testable = isTestable(testClass);

			if (testable) {
				addTestClass(testClass, javaArchive);
			}

			ZipExporter zipExporter = javaArchive.as(ZipExporter.class);

			Jar jar = new Jar(
				javaArchive.getName(),
				zipExporter.exportAsInputStream());

			analyzer.setJar(jar);

			DeploymentDescription deploymentDescription =
				new DeploymentDescription(javaArchive.getName(), javaArchive);

			deploymentDescription.shouldBeTestable(testable).shouldBeManaged(
				true);

			deployments.add(deploymentDescription);

			Manifest firstPassManifest = new Manifest(
				javaArchive.get(MANIFEST_PATH).getAsset().openStream());

			firstPassManifest.getMainAttributes().remove(
				new Name("Import-Package"));

			analyzer.mergeManifest(firstPassManifest);

			Manifest manifest = analyzer.calcManifest();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			manifest.write(baos);

			ByteArrayAsset byteArrayAsset = new ByteArrayAsset(
				baos.toByteArray());

			replaceManifest(javaArchive, byteArrayAsset);

			return deployments;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public File getBndFile(TestClass testClass) {
		if (testClass.isAnnotationPresent(BndFile.class)) {
			BndFile annotation = testClass.getAnnotation(BndFile.class);

			return new File(annotation.value());
		}
		else {
			return this.bndFile;
		}
	}

	public void setBndFile(File bndFile) {
		this.bndFile = bndFile;
	}

	public void setCommonBndFile(File commonBndFile) {
		this.commonBndFile = commonBndFile;
	}

	protected DeploymentScenarioGenerator getDefaultDeploymentScenarioGenerator() {
		AnnotationDeploymentScenarioGenerator
			annotationDeploymentScenarioGenerator =
				new AnnotationDeploymentScenarioGenerator();

		annotationDeploymentScenarioGenerator = injector.get().inject(
			annotationDeploymentScenarioGenerator);

		return annotationDeploymentScenarioGenerator;
	}

	private void addTestClass(TestClass testClass, JavaArchive javaArchive) {
		javaArchive.addClass(testClass.getJavaClass());
	}

	private boolean isTestable(TestClass testClass) {
		return !testClass.isAnnotationPresent(RunAsClient.class);
	}

	private void replaceManifest(
		Archive<?> archive, ByteArrayAsset byteArrayAsset) {

		archive.delete(MANIFEST_PATH);

		archive.add(byteArrayAsset, MANIFEST_PATH);
	}

}
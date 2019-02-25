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

package com.liferay.arquillian.extension.junit.bridge.statement;

import aQute.bnd.build.Project;
import aQute.bnd.build.ProjectBuilder;
import aQute.bnd.build.Workspace;
import aQute.bnd.osgi.AbstractResource;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

import com.liferay.arquillian.extension.junit.bridge.activator.ArquillianBundleActivator;
import com.liferay.arquillian.extension.junit.bridge.jmx.JMXProxyUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.management.ObjectName;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.runners.model.Statement;

import org.osgi.jmx.framework.FrameworkMBean;

/**
 * @author Shuyang Zhou
 */
public class DeploymentStatement extends Statement {

	public DeploymentStatement(Statement statement) {
		_statement = statement;
	}

	@Override
	public void evaluate() throws Throwable {
		FrameworkMBean frameworkMBean = JMXProxyUtil.newProxy(
			_frameworkObjectName, FrameworkMBean.class);

		long bundleId = _installBundle(frameworkMBean, _create());

		frameworkMBean.startBundle(bundleId);

		try {
			_statement.evaluate();
		}
		finally {
			frameworkMBean.uninstallBundle(bundleId);
		}
	}

	private static Path _create() {
		try (Workspace workspace = new Workspace(_buildDir);
			Project project = new Project(workspace, _buildDir);

			ProjectBuilder projectBuilder = _createProjectBuilder(project);

			Jar jar = _createJar(project, projectBuilder)) {

			ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream();

			jar.write(byteArrayOutputStream);

			ZipImporter zipImporter = ShrinkWrap.create(ZipImporter.class);

			zipImporter.importFrom(
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

			JavaArchive javaArchive = zipImporter.as(JavaArchive.class);

			_process(javaArchive);

			Path path = Files.createTempFile(null, ".jar");

			ZipExporter zipExporter = javaArchive.as(ZipExporter.class);

			try (InputStream inputStream = zipExporter.exportAsInputStream()) {
				Files.copy(
					inputStream, path, StandardCopyOption.REPLACE_EXISTING);
			}

			return path;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static Jar _createJar(
			Project project, ProjectBuilder projectBuilder)
		throws Exception {

		Jar jar = projectBuilder.build();

		jar.setManifest(_createManifest(jar, project));

		jar.putResource(
			"/arquillian.remote.marker",
			new AbstractResource(System.currentTimeMillis()) {

				@Override
				protected byte[] getBytes() throws Exception {
					return new byte[0];
				}

			});

		return jar;
	}

	private static Manifest _createManifest(Jar jar, Project project)
		throws Exception {

		Analyzer analyzer = new Analyzer();

		analyzer.setProperties(project.getProperties());
		analyzer.setJar(jar);

		Manifest manifest = analyzer.calcManifest();

		Set<String> importPackages = new HashSet<>();

		Collections.addAll(importPackages, _IMPORTS_PACKAGES);

		Attributes mainAttributes = manifest.getMainAttributes();

		importPackages.addAll(
			StringUtil.split(mainAttributes.getValue(_importPackageName)));

		importPackages.remove(
			"com.liferay.arquillian.extension.junit.bridge.junit");

		StringBundler sb = new StringBundler(importPackages.size() * 2);

		for (String attributeValue : importPackages) {
			sb.append(attributeValue);
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		mainAttributes.put(_importPackageName, sb.toString());

		mainAttributes.put(
			_bundleActivatorName,
			ArquillianBundleActivator.class.getCanonicalName());

		return manifest;
	}

	private static ProjectBuilder _createProjectBuilder(Project project)
		throws IOException {

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

	private static void _process(JavaArchive javaArchive) {
		javaArchive.addPackages(
			true, "com.liferay.arquillian.extension.junit.bridge");
	}

	private long _installBundle(FrameworkMBean frameworkMBean, Path path)
		throws Exception {

		URI uri = path.toUri();

		URL url = uri.toURL();

		try {
			return frameworkMBean.installBundleFromURL(
				url.getPath(), url.toExternalForm());
		}
		finally {
			Files.delete(path);
		}
	}

	private static final String[] _IMPORTS_PACKAGES = {
		"javax.management", "javax.management.*", "javax.naming",
		"javax.naming.*", "org.junit.internal", "org.junit.internal.runners",
		"org.junit.internal.runners.model",
		"org.junit.internal.runners.statements", "org.junit.rules",
		"org.junit.runner.manipulation", "org.junit.runner.notification",
		"org.junit.runners", "org.junit.runners.model", "org.osgi.framework",
		"org.osgi.framework.wiring", "org.osgi.service.packageadmin",
		"org.osgi.service.startlevel", "org.osgi.util.tracker"
	};

	private static final File _buildDir = new File(
		System.getProperty("user.dir"));
	private static final Attributes.Name _bundleActivatorName =
		new Attributes.Name("Bundle-Activator");
	private static final ObjectName _frameworkObjectName;
	private static final Attributes.Name _importPackageName =
		new Attributes.Name("Import-Package");

	static {
		try {
			_frameworkObjectName = new ObjectName("osgi.core:type=framework,*");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final Statement _statement;

}
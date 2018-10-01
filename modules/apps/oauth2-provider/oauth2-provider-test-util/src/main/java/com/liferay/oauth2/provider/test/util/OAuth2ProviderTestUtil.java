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

package com.liferay.oauth2.provider.test.util;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.shrinkwrap.osgi.api.BndProjectBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.osgi.framework.BundleActivator;

/**
 * @author Carlos Sierra Andrés
 */
public class OAuth2ProviderTestUtil {

	public static Archive<?> getDeployment(
			Class<? extends BundleActivator> bundleActivatorClass)
		throws Exception {

		String javaClassPathString = System.getProperty("java.class.path");

		String[] javaClassPaths = StringUtil.split(
			javaClassPathString, File.pathSeparator);

		BndProjectBuilder bndProjectBuilder = ShrinkWrap.create(
			BndProjectBuilder.class);

		for (String javaClassPath : javaClassPaths) {
			File file = new File(javaClassPath);

			if (file.isDirectory() ||
				StringUtil.endsWith(javaClassPath, ".zip") ||
				StringUtil.endsWith(javaClassPath, ".jar")) {

				bndProjectBuilder.addClassPath(file);
			}
		}

		File bndFile = new File("bnd.bnd");

		bndProjectBuilder = bndProjectBuilder.setBndFile(bndFile);

		JavaArchive javaArchive = bndProjectBuilder.as(JavaArchive.class);

		javaArchive.addClass(bundleActivatorClass);

		ZipExporter zipExporter = javaArchive.as(ZipExporter.class);

		Jar jar = new Jar(
			javaArchive.getName(), zipExporter.exportAsInputStream());

		Analyzer analyzer = new Analyzer();
		Properties analyzerProperties = new Properties();

		analyzerProperties.putAll(analyzer.loadProperties(bndFile));

		analyzer.setJar(jar);

		Node node = javaArchive.get("META-INF/MANIFEST.MF");

		Asset asset = node.getAsset();

		try (InputStream inputStream = asset.openStream()) {
			Manifest manifest = new Manifest(inputStream);

			Attributes attributes = manifest.getMainAttributes();

			attributes.remove(new Attributes.Name("Import-Package"));

			attributes.putValue(
				"Bundle-Activator", bundleActivatorClass.getName());

			analyzer.mergeManifest(manifest);
		}

		Manifest manifest = analyzer.calcManifest();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		manifest.write(baos);

		Asset byteArrayAsset = new ByteArrayAsset(baos.toByteArray());

		javaArchive.delete("META-INF/MANIFEST.MF");

		javaArchive.add(byteArrayAsset, "META-INF/MANIFEST.MF");

		return javaArchive;
	}

}
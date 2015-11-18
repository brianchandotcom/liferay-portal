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

import com.liferay.arquillian.extension.junit.bridge.LiferayArquillianJUnitBridgeExtension;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.arquillian.extension.junit.bridge.observer.JUnitBridgeObserver;
import com.liferay.arquillian.extension.junit.bridge.util.FrameworkMethodComparator;
import com.liferay.portal.kernel.util.CharPool;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jboss.arquillian.container.test.spi.RemoteLoadableExtension;
import org.jboss.arquillian.container.test.spi.TestRunner;
import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.junit.container.JUnitTestRunner;
import org.jboss.arquillian.junit.event.BeforeRules;
import org.jboss.osgi.metadata.OSGiManifestBuilder;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class JUnitBridgeAuxiliaryArchiveAppender
	implements AuxiliaryArchiveAppender {

	@Override
	public Archive<?> createAuxiliaryArchive() {
		JavaArchive javaArchive = ShrinkWrap.create(
			JavaArchive.class, "arquillian-junit-bridge.jar");

		javaArchive.add(EmptyAsset.INSTANCE, "/arquillian.remote.marker");
		javaArchive.addAsServiceProviderAndClasses(
			RemoteLoadableExtension.class,
			LiferayArquillianJUnitBridgeExtension.class);
		javaArchive.addAsServiceProviderAndClasses(
			TestRunner.class, JUnitTestRunner.class);
		javaArchive.addClasses(
			Arquillian.class, JUnitBridgeObserver.class,
			FrameworkMethodComparator.class);
		javaArchive.addPackages(
			false, BeforeRules.class.getPackage(),
			org.jboss.arquillian.junit.Arquillian.class.getPackage());

		OSGiManifestBuilder osgiManifestBuilder =
			OSGiManifestBuilder.newInstance();

		osgiManifestBuilder.addBundleManifestVersion(1);

		osgiManifestBuilder.addImportPackages(
			getPackageNames(getJarFile(Test.class)));

		javaArchive.add(
			new ByteArrayAsset(osgiManifestBuilder.openStream()),
			"/META-INF/MANIFEST.MF");

		return javaArchive;
	}

	protected File getJarFile(Class<?> clazz) {
		String className = clazz.getName();

		String resourceName = className.replace(
			CharPool.PERIOD, CharPool.SLASH);

		ClassLoader classLoader = clazz.getClassLoader();

		URL url = classLoader.getResource(resourceName.concat(".class"));

		String file = url.getFile();

		if (file.startsWith("file:")) {
			file = file.substring("file:".length());
		}

		int index = file.lastIndexOf(CharPool.EXCLAMATION);

		if (index != -1) {
			file = file.substring(0, index);
		}

		return new File(file);
	}

	protected String[] getPackageNames(File file) {
		Set<String> packageNames = new HashSet<>();

		try (JarFile jarFile = new JarFile(file)) {
			Enumeration<JarEntry> enumeration = jarFile.entries();

			while (enumeration.hasMoreElements()) {
				JarEntry jarEntry = enumeration.nextElement();

				if (jarEntry.isDirectory()) {
					continue;
				}

				String name = jarEntry.getName();

				if (!name.endsWith(".class")) {
					continue;
				}

				int index = name.lastIndexOf('/');

				if (index < 0) {
					continue;
				}

				name = name.substring(0, index);

				if (name.isEmpty()) {
					continue;
				}

				packageNames.add(name.replace('/', '.'));
			}
		}
		catch (IOException ioe) {
			throw new IllegalArgumentException(
				"Unable to get package names for " + file, ioe);
		}

		return packageNames.toArray(new String[packageNames.size()]);
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.bundle.builder.commands;

import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Processor;

import aQute.lib.strings.Strings;

import com.liferay.osgi.bundle.builder.OSGiBundleBuilderArgs;
import com.liferay.osgi.bundle.builder.internal.util.FileUtil;
import com.liferay.osgi.bundle.builder.internal.util.StringUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.jar.Manifest;

/**
 * @author David Truong
 */
public abstract class BaseCommand implements Command {

	@Override
	public void build(OSGiBundleBuilderArgs osgiBundleBuilderArgs)
		throws Exception {

		Properties properties = FileUtil.readProperties(
			osgiBundleBuilderArgs.getBndFile());

		properties.setProperty(
			"-plugin",
			StringUtil.join(osgiBundleBuilderArgs.getPlugins(), ','));

		try (Builder builder = new Builder(new Processor(properties, false))) {
			builder.setBase(osgiBundleBuilderArgs.getBaseDir());

			File classesDir = osgiBundleBuilderArgs.getClassesDir();

			if ((classesDir != null) && classesDir.exists()) {
				Jar classesJar = new Jar(classesDir);

				classesJar.setManifest(new Manifest());

				File resourcesDir = osgiBundleBuilderArgs.getResourcesDir();

				if ((resourcesDir != null) && resourcesDir.exists()) {
					Jar resourcesJar = new Jar(resourcesDir);

					classesJar.addAll(resourcesJar);
				}

				builder.setJar(classesJar);
			}

			List<File> classpath = osgiBundleBuilderArgs.getClasspath();

			if ((classpath != null) && !classpath.isEmpty()) {
				List<Object> buildPath = new ArrayList<>(classpath.size());

				for (File file : classpath) {
					if (!file.exists()) {
						continue;
					}

					if (file.isDirectory()) {
						Jar jar = new Jar(file);

						builder.addClose(jar);

						builder.updateModified(
							jar.lastModified(), file.getPath());

						buildPath.add(jar);
					}
					else {
						builder.updateModified(
							file.lastModified(), file.getPath());

						buildPath.add(file);
					}
				}

				builder.setClasspath(buildPath);
				builder.setProperty(
					"project.buildpath",
					Strings.join(File.pathSeparator, buildPath));
			}

			Jar jar = builder.build();

			writeOutput(jar, osgiBundleBuilderArgs);
		}
	}

	protected abstract void writeOutput(
			Jar jar, OSGiBundleBuilderArgs osgiBundleBuilderArgs)
		throws Exception;

}
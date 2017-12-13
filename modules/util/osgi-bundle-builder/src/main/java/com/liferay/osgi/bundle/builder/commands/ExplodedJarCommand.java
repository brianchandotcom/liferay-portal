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

package com.liferay.osgi.bundle.builder.commands;

import aQute.bnd.osgi.FileResource;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;

import aQute.lib.io.IO;

import com.beust.jcommander.Parameters;

import com.liferay.osgi.bundle.builder.OSGiBundleBuilderArgs;
import com.liferay.osgi.bundle.builder.internal.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.util.Map;

/**
 * @author David Truong
 */
@Parameters(
	commandDescription = "Generates an exploded JAR of the OSGi bundle.",
	commandNames = "exploded-jar"
)
public class ExplodedJarCommand extends BaseCommand {

	@Override
	protected void writeOutput(
			Jar jar, OSGiBundleBuilderArgs osgiBundleBuilderArgs)
		throws Exception {

		long lastModified = jar.lastModified();

		File outputDir = osgiBundleBuilderArgs.getOutputFile();

		FileUtil.createDirectories(outputDir);

		Map<String, Resource> resources = jar.getResources();

		for (Map.Entry<String, Resource> entry : resources.entrySet()) {
			File outputFile = IO.getFile(outputDir, entry.getKey());

			Resource resource = entry.getValue();

			if (resource instanceof FileResource) {
				FileResource fileResource = (FileResource)resource;

				if (outputFile.equals(fileResource.getFile())) {
					continue;
				}
			}

			if (!outputFile.exists() ||
				(outputFile.lastModified() < lastModified)) {

				FileUtil.createDirectories(outputFile.getParentFile());

				try (OutputStream outputStream = new FileOutputStream(
						outputFile)) {

					IO.copy(resource.openInputStream(), outputStream);
				}
			}
		}

		File manifestDir = new File(outputDir, "META-INF");

		FileUtil.createDirectories(manifestDir);

		File manifestFile = new File(manifestDir, "MANIFEST.MF");

		try (OutputStream outputStream = new FileOutputStream(manifestFile)) {
			jar.writeManifest(outputStream);
		}
	}

}
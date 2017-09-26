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

import aQute.bnd.osgi.Jar;

import com.beust.jcommander.Parameters;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.nio.file.Files;

/**
 * @author David Truong
 */
@Parameters(
	commandDescription = "Generates the MANIFEST.MF file of the OSGi bundle.",
	commandNames = "manifest"
)
public class ManifestCommand extends BaseCommand {

	@Override
	protected void writeOutput(Jar jar) throws Exception {
		File output = getOutput();

		Files.createDirectories(output.toPath());

		if (output.isFile()) {
			try (OutputStream outputStream = new FileOutputStream(output)) {
				jar.writeManifest(outputStream);
			}
		}
		else {
			try (OutputStream outputStream = new FileOutputStream(
					new File(output, "MANIFEST.MF"))) {

				jar.writeManifest(outputStream);
			}
		}
	}

}
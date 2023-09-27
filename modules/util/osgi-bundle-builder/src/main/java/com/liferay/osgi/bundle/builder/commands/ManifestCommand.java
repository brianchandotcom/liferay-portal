/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.bundle.builder.commands;

import aQute.bnd.osgi.Jar;

import com.beust.jcommander.Parameters;

import com.liferay.osgi.bundle.builder.OSGiBundleBuilderArgs;
import com.liferay.osgi.bundle.builder.internal.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Generates the MANIFEST.MF file of the OSGi bundle.",
	commandNames = "manifest"
)
public class ManifestCommand extends BaseCommand {

	@Override
	protected void writeOutput(
			Jar jar, OSGiBundleBuilderArgs osgiBundleBuilderArgs)
		throws Exception {

		File outputFile = osgiBundleBuilderArgs.getOutputFile();

		FileUtil.createDirectories(outputFile.getParentFile());

		try (OutputStream outputStream = new FileOutputStream(outputFile)) {
			jar.writeManifest(outputStream);
		}
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.bnd.resource;

import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.FileResource;
import aQute.bnd.osgi.Jar;
import aQute.bnd.service.verifier.VerifierPlugin;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Gregory Amerson
 */
public class AddResourceVerifierPlugin implements VerifierPlugin {

	@Override
	public void verify(Analyzer analyzer) throws Exception {
		Parameters parameters = OSGiHeader.parseHeader(
			analyzer.getProperty("-add-resource"));

		if (parameters.isEmpty()) {
			return;
		}

		Jar jar = analyzer.getJar();

		for (String path : parameters.keySet()) {
			Path jspClassesDir = Paths.get(path);

			if (!Files.exists(jspClassesDir)) {
				continue;
			}

			Files.walkFileTree(
				jspClassesDir,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(
							Path path, BasicFileAttributes basicFileAttributes)
						throws IOException {

						String relativePathString = String.valueOf(
							jspClassesDir.relativize(path));

						jar.putResource(
							relativePathString.replace('\\', '/'),
							new FileResource(path.toAbsolutePath()), true);

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

}
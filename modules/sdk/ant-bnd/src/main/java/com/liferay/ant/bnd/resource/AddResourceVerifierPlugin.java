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

package com.liferay.ant.bnd.resource;

import aQute.bnd.header.OSGiHeader;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.FileResource;
import aQute.bnd.osgi.Jar;
import aQute.bnd.osgi.Resource;
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

						Resource resource = new FileResource(
							path.toAbsolutePath());

						Path relativePath = jspClassesDir.relativize(path);

						jar.putResource(
							relativePath.toString(), resource, true);

						return FileVisitResult.CONTINUE;
					}

				});
		}
	}

}
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

package com.liferay.portal.report.stream.internal;

import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.report.stream.OutputStreamProvider;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true, property = {"name=tempfile"})
public class TempFileOutputStreamProvider implements OutputStreamProvider {

	@Override
	public OutputStreamProvider.OutputStreamInformation create(String hint) {
		try {
			Path tempDirectory = Files.createTempDirectory("tempDir");

			final Path tempFile = Files.createTempFile(
				tempDirectory, hint, ".log");

			return new OutputStreamInformation() {

				@Override
				public String getDescription() {
					return tempFile.toAbsolutePath().toString();
				}

				@Override
				public OutputStream getOutputStream() {
					try {
						return StreamUtil.uncloseable(
							new FileOutputStream(tempFile.toFile()));
					}
					catch (FileNotFoundException e) {
						throw new RuntimeException(e);
					}
				}

			};
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
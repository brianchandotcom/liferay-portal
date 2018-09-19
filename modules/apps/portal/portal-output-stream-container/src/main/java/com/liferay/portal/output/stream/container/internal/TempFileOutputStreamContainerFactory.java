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

package com.liferay.portal.output.stream.container.internal;

import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.output.stream.container.OutputStreamContainer;
import com.liferay.portal.output.stream.container.OutputStreamContainerFactory;

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
@Component(
	immediate = true, property = "name=temp_file",
	service = OutputStreamContainerFactory.class
)
public class TempFileOutputStreamContainerFactory
	implements OutputStreamContainerFactory {

	@Override
	public OutputStreamContainer create(String hint) {
		try {
			Path tempDirectoryPath = Files.createTempDirectory(
				"com_liferay_portal_output_stream_container_internal_" +
					"TempFileOutputStreamContainerFactory");

			final Path tempFilePath = Files.createTempFile(
				tempDirectoryPath, hint, ".log");

			return new OutputStreamContainer() {

				@Override
				public String getDescription() {
					Path absolutePath = tempFilePath.toAbsolutePath();

					return absolutePath.toString();
				}

				@Override
				public OutputStream getOutputStream() {
					try {
						return StreamUtil.uncloseable(
							new FileOutputStream(tempFilePath.toFile()));
					}
					catch (FileNotFoundException fnfe) {
						throw new RuntimeException(fnfe);
					}
				}

			};
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

}
/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.metadata;

import com.liferay.portal.kernel.exception.SystemException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;

/**
 * @author Miguel Pastor
 * @author Alexander Chow
 */
public class TikaRawMetadataProcessor extends XugglerRawMetadataProcessor {

	@Override
	public Metadata extractMetadata(
			String extension, String mimeType, File file)
		throws SystemException {

		Metadata metadata = super.extractMetadata(
			extension, mimeType, file);

		try {
			InputStream inputStream = new FileInputStream(file);

			return extractMetadata(inputStream, metadata);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	@Override
	public Metadata extractMetadata(
			String extension, String mimeType, InputStream inputStream)
		throws SystemException {

		Metadata metadata = super.extractMetadata(
			extension, mimeType, inputStream);

		try {
			return extractMetadata(inputStream, metadata);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	public void setTika(Tika tika) {
		_tika = tika;
	}

	protected Metadata extractMetadata(
			InputStream inputStream, Metadata metadata)
		throws IOException {

		if (metadata == null) {
			metadata = new Metadata();
		}

		_tika.parse(inputStream, metadata);

		// Remove potential security risks

		metadata.remove(XMPDM.ABS_PEAK_AUDIO_FILE_PATH.getName());
		metadata.remove(XMPDM.RELATIVE_PEAK_AUDIO_FILE_PATH.getName());
		
		return metadata;
	}

	private Tika _tika;

}
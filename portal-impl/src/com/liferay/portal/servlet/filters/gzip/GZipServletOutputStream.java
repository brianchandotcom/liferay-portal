/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet.filters.gzip;

import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.OutputStream;

import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;

/**
 * @author Shuyang Zhou
 * @author Raymond Augé
 */
public class GZipServletOutputStream extends ServletOutputStream {

	public GZipServletOutputStream(OutputStream outputStream)
		throws IOException {

		_gZipOutputStream = new GZIPOutputStream(outputStream) {

			{
				def.setLevel(PropsValues.GZIP_COMPRESSION_LEVEL);
			}

		};
	}

	@Override
	public void close() throws IOException {
		_gZipOutputStream.close();
	}

	@Override
	public void flush() throws IOException {
		_gZipOutputStream.flush();
	}

	@Override
	public void write(byte[] bytes) throws IOException {
		_gZipOutputStream.write(bytes);
	}

	@Override
	public void write(byte[] bytes, int offset, int length) throws IOException {
		_gZipOutputStream.write(bytes, offset, length);
	}

	@Override
	public void write(int b) throws IOException {
		_gZipOutputStream.write(b);
	}

	private GZIPOutputStream _gZipOutputStream;

}
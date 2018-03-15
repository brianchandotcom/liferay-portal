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

package com.liferay.saml.opensaml.integration.internal.transport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.AbstractHttpEntity;

/**
 * @author Mika Koivisto
 */
public class OutputStreamHttpEntity extends AbstractHttpEntity {

	public OutputStreamHttpEntity(ByteArrayOutputStream byteArrayOutputStream) {
		this(byteArrayOutputStream, null);
	}

	public OutputStreamHttpEntity(
		ByteArrayOutputStream byteArrayOutputStream, String contentType) {

		_byteArrayOutputStream = byteArrayOutputStream;

		setContentType(contentType);
	}

	@Override
	public InputStream getContent()
		throws IOException, UnsupportedOperationException {

		return new ByteArrayInputStream(_byteArrayOutputStream.toByteArray());
	}

	@Override
	public long getContentLength() {
		return _byteArrayOutputStream.size();
	}

	@Override
	public boolean isRepeatable() {
		return true;
	}

	@Override
	public boolean isStreaming() {
		return false;
	}

	@Override
	public void writeTo(OutputStream outstream) throws IOException {
	}

	private final ByteArrayOutputStream _byteArrayOutputStream;

}
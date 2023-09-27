/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public void writeTo(OutputStream outputStream) throws IOException {
	}

	private final ByteArrayOutputStream _byteArrayOutputStream;

}
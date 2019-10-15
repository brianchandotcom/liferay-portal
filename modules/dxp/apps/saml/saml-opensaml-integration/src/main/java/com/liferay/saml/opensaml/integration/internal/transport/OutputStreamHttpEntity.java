/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
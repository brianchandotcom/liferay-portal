/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;

import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Tina Tian
 */
public class URLTemplateResource implements TemplateResource {

	/**
	 * The empty constructor is required by {@link java.io.Externalizable}. Do
	 * not use this for any other purpose.
	 */
	public URLTemplateResource() {
	}

	public URLTemplateResource(String templateId, URL templateURL) {
		if (Validator.isNull(templateId)) {
			throw new IllegalArgumentException("Template ID is null");
		}

		if (templateURL == null) {
			throw new IllegalArgumentException("Template URL is null");
		}

		_templateId = templateId;
		_templateURL = templateURL;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof URLTemplateResource)) {
			return false;
		}

		URLTemplateResource urlTemplateResource = (URLTemplateResource)obj;

		if (_templateId.equals(urlTemplateResource._templateId) &&
			_templateURL.equals(urlTemplateResource._templateURL)) {

			return true;
		}

		return false;
	}

	@Override
	public long getLastModified() {
		URLConnection urlConnection = null;

		try {
			urlConnection = _templateURL.openConnection();

			if (urlConnection instanceof JarURLConnection) {
				JarURLConnection jarURLConnection =
					(JarURLConnection)urlConnection;

				URL url = jarURLConnection.getJarFileURL();

				String protocol = url.getProtocol();

				if (protocol.equals("file")) {
					return new File(url.getFile()).lastModified();
				}
				else {
					urlConnection = url.openConnection();
				}
			}

			return urlConnection.getLastModified();
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to get last modified time for template " + _templateId,
				ioe);

			return 0;
		}
		finally {
			if (urlConnection != null) {
				try {
					urlConnection.getInputStream().close();
				}
				catch (IOException ioe) {
				}
			}
		}
	}

	@Override
	public Reader getReader() throws IOException {
		URLConnection urlConnection = _templateURL.openConnection();

		return new InputStreamReader(
			urlConnection.getInputStream(), TemplateConstants.DEFAUT_ENCODING);
	}

	@Override
	public String getTemplateId() {
		return _templateId;
	}

	@Override
	public int hashCode() {
		return _templateId.hashCode() * 11 + _templateURL.hashCode();
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		_templateId = objectInput.readUTF();
		_templateURL = new URL(objectInput.readUTF());
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeUTF(_templateId);
		objectOutput.writeUTF(_templateURL.toExternalForm());
	}

	private static Log _log = LogFactoryUtil.getLog(URLTemplateResource.class);

	private String _templateId;
	private URL _templateURL;

}
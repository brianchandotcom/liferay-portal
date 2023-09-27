/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.URLUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;

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

		_templateURLExternalForm = templateURL.toExternalForm();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof URLTemplateResource)) {
			return false;
		}

		URLTemplateResource urlTemplateResource = (URLTemplateResource)object;

		if (_templateId.equals(urlTemplateResource._templateId) &&
			_templateURLExternalForm.equals(
				urlTemplateResource._templateURLExternalForm)) {

			return true;
		}

		return false;
	}

	@Override
	public long getLastModified() {
		try {
			return URLUtil.getLastModifiedTime(_templateURL);
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to get last modified time for template " + _templateId,
				ioException);

			return 0;
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
		return (_templateId.hashCode() * 11) +
			_templateURLExternalForm.hashCode();
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		_templateId = objectInput.readUTF();

		_templateURLExternalForm = objectInput.readUTF();

		_templateURL = new URL(_templateURLExternalForm);
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeUTF(_templateId);
		objectOutput.writeUTF(_templateURLExternalForm);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		URLTemplateResource.class);

	private String _templateId;
	private URL _templateURL;
	private String _templateURLExternalForm;

}
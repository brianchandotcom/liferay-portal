/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template;

import com.liferay.portal.kernel.io.unsync.UnsyncCharArrayWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.template.TemplateResource;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Tina Tian
 */
public class CacheTemplateResource implements TemplateResource {

	/**
	 * The empty constructor is required by {@link java.io.Externalizable}. Do
	 * not use this for any other purpose.
	 */
	public CacheTemplateResource() {
	}

	public CacheTemplateResource(TemplateResource templateResource) {
		if (templateResource == null) {
			throw new IllegalArgumentException("Template resource is null");
		}

		_templateResource = templateResource;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CacheTemplateResource)) {
			return false;
		}

		CacheTemplateResource cacheTemplateResource =
			(CacheTemplateResource)object;

		if (_templateResource.equals(cacheTemplateResource._templateResource)) {
			return true;
		}

		return false;
	}

	public TemplateResource getInnerTemplateResource() {
		return _templateResource;
	}

	@Override
	public long getLastModified() {
		return _lastModified;
	}

	@Override
	public Reader getReader() throws IOException {
		String templateContent = _templateContent.get();

		if (templateContent != null) {
			return new UnsyncStringReader(templateContent);
		}

		try (Reader reader = _templateResource.getReader()) {
			char[] buffer = new char[1024];

			int result = -1;

			UnsyncCharArrayWriter unsyncCharArrayWriter =
				new UnsyncCharArrayWriter();

			while ((result = reader.read(buffer)) != -1) {
				unsyncCharArrayWriter.write(buffer, 0, result);
			}

			templateContent = unsyncCharArrayWriter.toString();

			_templateContent.set(templateContent);
		}

		return new UnsyncStringReader(templateContent);
	}

	@Override
	public String getTemplateId() {
		return _templateResource.getTemplateId();
	}

	@Override
	public int hashCode() {
		return _templateResource.hashCode();
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_lastModified = objectInput.readLong();
		_templateResource = (TemplateResource)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(_lastModified);
		objectOutput.writeObject(_templateResource);
	}

	private long _lastModified = System.currentTimeMillis();
	private final AtomicReference<String> _templateContent =
		new AtomicReference<>();
	private TemplateResource _templateResource;

}
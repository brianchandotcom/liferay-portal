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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;

/**
 * @author Tina Tian
 */
public class StringTemplateResource implements TemplateResource {

	/**
	 * The empty constructor is required by {@link java.io.Externalizable}. Do
	 * not use this for any other purpose.
	 */
	public StringTemplateResource() {
	}

	public StringTemplateResource(String templateId, String templateContent) {
		if (Validator.isNull(templateId)) {
			throw new IllegalArgumentException("Template ID is null");
		}

		if (Validator.isNull(templateContent)) {
			throw new IllegalArgumentException("Template content is null");
		}

		_templateId = templateId;
		_templateContent = templateContent;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof StringTemplateResource)) {
			return false;
		}

		StringTemplateResource stringTemplateResource =
			(StringTemplateResource)obj;

		if (_templateId.equals(stringTemplateResource._templateId) &&
			_templateContent.equals(stringTemplateResource._templateContent)) {

			return true;
		}

		return false;
	}

	public String getContent() {
		return _templateContent;
	}

	@Override
	public long getLastModified() {
		return _lastModified;
	}

	@Override
	public Reader getReader() {
		return new UnsyncStringReader(_templateContent);
	}

	@Override
	public String getTemplateId() {
		return _templateId;
	}

	@Override
	public int hashCode() {
		return _templateId.hashCode() * 11 + _templateContent.hashCode();
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		_lastModified = objectInput.readLong();
		_templateContent = objectInput.readUTF();
		_templateId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(_lastModified);
		objectOutput.writeUTF(_templateContent);
		objectOutput.writeUTF(_templateId);
	}

	private long _lastModified = System.currentTimeMillis();
	private String _templateContent;
	private String _templateId;

}
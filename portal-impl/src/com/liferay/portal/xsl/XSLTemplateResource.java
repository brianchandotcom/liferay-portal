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

package com.liferay.portal.xsl;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;

import java.util.Collections;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class XSLTemplateResource implements TemplateResource {

	/**
	 * The empty constructor is required by {@link java.io.Externalizable}. Do
	 * not use this for any other purpose.
	 */
	public XSLTemplateResource() {
	}

	public XSLTemplateResource(
		String templateId, String script, Map<String, String> tokens,
		String languageId, String xml) {

		if (Validator.isNull(templateId)) {
			throw new IllegalArgumentException("Template ID is null");
		}

		if (Validator.isNull(script)) {
			throw new IllegalArgumentException("Script is null");
		}

		if (tokens == null) {
			throw new IllegalArgumentException("Tokens map is null");
		}

		if (Validator.isNull(xml)) {
			throw new IllegalArgumentException("XML is null");
		}

		_templateId = templateId;
		_script = script;
		_tokens = tokens;
		_languageId = languageId;
		_xml = xml;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof XSLTemplateResource)) {
			return false;
		}

		XSLTemplateResource xslTemplateResource = (XSLTemplateResource)obj;

		if (_templateId.equals(xslTemplateResource._templateId) &&
			_script.equals(xslTemplateResource._script) &&
			_xml.equals(xslTemplateResource._xml) &&
			_tokens.equals(xslTemplateResource._tokens)) {

			return true;
		}

		return false;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public long getLastModified() {
		return _lastModified;
	}

	public Reader getReader() {
		return new UnsyncStringReader(_script);
	}

	public String getTemplateId() {
		return _templateId;
	}

	public Map<String, String> getTokens() {
		return Collections.unmodifiableMap(_tokens);
	}

	public Reader getXMLReader() {
		return new UnsyncStringReader(_xml);
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _templateId);

		hashCode = HashUtil.hash(hashCode, _script);
		hashCode = HashUtil.hash(hashCode, _xml);
		hashCode = HashUtil.hash(hashCode, _tokens);

		return hashCode;
	}

	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_templateId = objectInput.readUTF();
		_lastModified = objectInput.readLong();
		_script = objectInput.readUTF();
		_languageId = objectInput.readUTF();

		if (_languageId.equals(StringPool.BLANK)) {
			_languageId = null;
		}

		_tokens = (Map<String, String>)objectInput.readObject();
		_xml = objectInput.readUTF();
	}

	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeUTF(_templateId);
		objectOutput.writeLong(_lastModified);
		objectOutput.writeUTF(_script);

		if (_languageId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(_languageId);
		}

		objectOutput.writeObject(_tokens);
		objectOutput.writeUTF(_xml);
	}

	private String _languageId;
	private long _lastModified = System.currentTimeMillis();
	private String _script;
	private String _templateId;
	private Map<String, String> _tokens;
	private String _xml;

}
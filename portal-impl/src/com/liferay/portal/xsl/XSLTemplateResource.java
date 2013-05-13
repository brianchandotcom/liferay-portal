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

package com.liferay.portal.xsl;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;

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
		String templateId, String xsl, XSLURIResolver xslURIResolver,
		String xml) {

		if (Validator.isNull(templateId)) {
			throw new IllegalArgumentException("Template ID is null");
		}

		if (Validator.isNull(xsl)) {
			throw new IllegalArgumentException("XSL is null");
		}

		if (Validator.isNull(xml)) {
			throw new IllegalArgumentException("XML is null");
		}

		_templateId = templateId;
		_xsl = xsl;
		_xslURIResolver = xslURIResolver;
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
			_xsl.equals(xslTemplateResource._xsl) &&
			Validator.equals(
				_xslURIResolver, xslTemplateResource._xslURIResolver) &&
			_xml.equals(xslTemplateResource._xml)) {

			return true;
		}

		return false;
	}

	@Override
	public long getLastModified() {
		return _lastModified;
	}

	@Override
	public Reader getReader() {
		return new UnsyncStringReader(_xsl);
	}

	@Override
	public String getTemplateId() {
		return _templateId;
	}

	public Reader getXMLReader() {
		return new UnsyncStringReader(_xml);
	}

	public XSLURIResolver getXSLURIResolver() {
		return _xslURIResolver;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _templateId);

		hashCode = HashUtil.hash(hashCode, _xsl);
		hashCode = HashUtil.hash(hashCode, _xslURIResolver);
		hashCode = HashUtil.hash(hashCode, _xml);

		return hashCode;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_templateId = objectInput.readUTF();
		_lastModified = objectInput.readLong();
		_xsl = objectInput.readUTF();
		_xslURIResolver = (XSLURIResolver)objectInput.readObject();
		_xml = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeUTF(_templateId);
		objectOutput.writeLong(_lastModified);
		objectOutput.writeUTF(_xsl);
		objectOutput.writeObject(_xslURIResolver);
		objectOutput.writeUTF(_xml);
	}

	private long _lastModified = System.currentTimeMillis();
	private String _templateId;
	private String _xml;
	private String _xsl;
	private XSLURIResolver _xslURIResolver;

}
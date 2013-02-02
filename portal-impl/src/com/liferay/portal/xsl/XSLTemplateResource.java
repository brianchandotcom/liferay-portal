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
		String templateId, String xsl, String xml, XSLURIResolver uriResolver) {
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
		_xml = xml;
		_uriResolver = uriResolver;
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
			_xml.equals(xslTemplateResource._xml) &&
			Validator.equals(_uriResolver, xslTemplateResource._uriResolver)) {

			return true;
		}

		return false;
	}

	public long getLastModified() {
		return _lastModified;
	}

	public Reader getReader() {
		return new UnsyncStringReader(_xsl);
	}

	public String getTemplateId() {
		return _templateId;
	}

	public XSLURIResolver getURIResolver() {
		return _uriResolver;
	}

	public Reader getXMLReader() {
		return new UnsyncStringReader(_xml);
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, _templateId);

		hashCode = HashUtil.hash(hashCode, _xsl);
		hashCode = HashUtil.hash(hashCode, _xml);
		hashCode = HashUtil.hash(hashCode, _uriResolver);

		return hashCode;
	}

	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_templateId = objectInput.readUTF();
		_lastModified = objectInput.readLong();
		_xsl = objectInput.readUTF();
		_xml = objectInput.readUTF();

		Object object = objectInput.readObject();

		if (object instanceof XSLURIResolver) {
			_uriResolver = (XSLURIResolver)object;
		}
	}

	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeUTF(_templateId);
		objectOutput.writeLong(_lastModified);
		objectOutput.writeUTF(_xsl);
		objectOutput.writeUTF(_xml);

		if (_uriResolver != null) {
			objectOutput.writeObject(_uriResolver);
		}
		else {
			objectOutput.writeObject(_DUMMY_OBJECT);
		}
	}

	private static final Object _DUMMY_OBJECT = new Object();

	private long _lastModified = System.currentTimeMillis();

	private String _templateId;

	private XSLURIResolver _uriResolver;

	private String _xml;
	private String _xsl;

}
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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.xml.XMLSchema;
import com.liferay.portal.util.EntityResolver;

import org.xml.sax.InputSource;

/**
 * @author Marcellus Tavares
 */
public class XMLSchemaImpl implements XMLSchema {

	@Override
	public String getPublicId() {
		return _publicId;
	}

	@Override
	public String getSchemaLanguage() {
		return _schemaLanguage;
	}

	@Override
	public InputSource getSchemaSource() {
		EntityResolver entityResolver = new EntityResolver();

		return entityResolver.resolveEntity(_publicId, _systemId);
	}

	@Override
	public String getSystemId() {
		return _systemId;
	}

	public void setPublicId(String publicId) {
		_publicId = publicId;
	}

	public void setSchemaLanguage(String schemaLanguage) {
		_schemaLanguage = schemaLanguage;
	}

	public void setSystemId(String systemId) {
		_systemId = systemId;
	}

	private String _publicId;
	private String _schemaLanguage;
	private String _systemId;

}
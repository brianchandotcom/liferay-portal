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

package com.liferay.portal.template;

import com.liferay.portal.kernel.template.TemplateResource;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * @author Tina Tian
 */
public class StringTemplateResource implements TemplateResource {

	public StringTemplateResource(String templateId, String templateContent) {
		_templateId = templateId;
		_templateContent = templateContent;
		_lastModified = System.currentTimeMillis();
	}

	public String getContent() {
		return _templateContent;
	}

	public long getLastModified() {
		return _lastModified;
	}

	public Reader getReader() throws IOException {
		if (_templateContent == null) {
			return null;
		}

		return new StringReader(_templateContent);
	}

	public String getTemplateId() {
		return _templateId;
	}

	private long _lastModified;
	private String _templateContent;
	private String _templateId;

}
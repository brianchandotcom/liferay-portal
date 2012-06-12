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

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author Tina Tian
 */
public class JournalTemplateResource implements TemplateResource {

	public JournalTemplateResource(
		String templateId, JournalTemplate templateContent) {

		_templateId = templateId;
		_templateContent = templateContent;
	}

	public long getLastModified() {
		return _templateContent.getModifiedDate().getTime();
	}

	public Reader getReader() throws IOException {
		if (_templateContent == null) {
			return null;
		}

		String xsl = _templateContent.getXsl();

		return new UnsyncBufferedReader(
			new InputStreamReader(
				new UnsyncByteArrayInputStream(
					xsl.getBytes()), DEFAUT_ENCODING));
	}

	public String getTemplateId() {
		return _templateId;
	}

	private JournalTemplate _templateContent;
	private String _templateId;

}
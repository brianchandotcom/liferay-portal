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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.io.IOException;
import java.io.Reader;

/**
 * @author Tina Tian
 */
public class JournalTemplateResource implements TemplateResource {

	public JournalTemplateResource(
		String templateId, JournalTemplate journalTemplate) {

		if (templateId == null) {
			throw new IllegalArgumentException("Missing templateId");
		}

		if (journalTemplate == null) {
			throw new IllegalArgumentException("Missing journalTemplate");
		}

		_templateId = templateId;
		_journalTemplate = journalTemplate;
	}

	public long getLastModified() {
		return _journalTemplate.getModifiedDate().getTime();
	}

	public Reader getReader() throws IOException {
		if (_journalTemplate == null) {
			return null;
		}

		String xsl = _journalTemplate.getXsl();

		return new UnsyncStringReader(xsl);
	}

	public String getTemplateId() {
		return _templateId;
	}

	private JournalTemplate _journalTemplate;
	private String _templateId;

}
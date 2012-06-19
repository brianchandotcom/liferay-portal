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

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.io.Reader;

import java.util.Date;

/**
 * @author Tina Tian
 */
public class JournalTemplateResource implements TemplateResource {

	public JournalTemplateResource(
		String templateId, JournalTemplate journalTemplate) {

		if (templateId == null) {
			throw new IllegalArgumentException("Template ID is null");
		}

		if (journalTemplate == null) {
			throw new IllegalArgumentException("Journal template is null");
		}

		_templateId = templateId;
		_journalTemplate = journalTemplate;
	}

	public long getLastModified() {
		Date modifiedDate = _journalTemplate.getModifiedDate();

		return modifiedDate.getTime();
	}

	public Reader getReader() {
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
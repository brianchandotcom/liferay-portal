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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;

import java.util.Date;

/**
 * @author Tina Tian
 */
public class JournalTemplateResource implements TemplateResource {

	/**
	 * Required by {@link java.io.Externalizable}, do not use this for other
	 * purpose.
	 */
	public JournalTemplateResource() {
	}

	public JournalTemplateResource(
		String templateId, JournalTemplate journalTemplate) {

		if (Validator.isNull(templateId)) {
			throw new IllegalArgumentException("Template ID is null");
		}

		if (journalTemplate == null) {
			throw new IllegalArgumentException("Journal template is null");
		}

		_templateId = templateId;
		_journalTemplate = journalTemplate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalTemplateResource)) {
			return false;
		}

		JournalTemplateResource journalTemplateResource =
			(JournalTemplateResource)obj;

		if (_templateId.equals(journalTemplateResource._templateId) &&
			_journalTemplate.equals(journalTemplateResource._journalTemplate)) {

			return true;
		}

		return false;
	}

	public long getLastModified() {
		Date modifiedDate = _journalTemplate.getModifiedDate();

		return modifiedDate.getTime();
	}

	public Reader getReader() {
		String xsl = _journalTemplate.getXsl();

		return new UnsyncStringReader(xsl);
	}

	public String getTemplateId() {
		return _templateId;
	}

	@Override
	public int hashCode() {
		return _templateId.hashCode() * 11 + _journalTemplate.hashCode();
	}

	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_templateId = objectInput.readUTF();

		long journalTemplateId = objectInput.readLong();

		try {
			_journalTemplate =
				JournalTemplateLocalServiceUtil.getJournalTemplate(
					journalTemplateId);
		}
		catch (Exception e) {
			throw new IOException(
				"Unable to retrieve JournalTemplate with id " +
					journalTemplateId, e);
		}
	}

	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeUTF(_templateId);
		objectOutput.writeLong(_journalTemplate.getId());
	}

	private JournalTemplate _journalTemplate;
	private String _templateId;

}
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
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;

import java.util.Date;

/**
 * @author Tina Tian
 * @author Juan Fernández
 */
public class DDMTemplateResource implements TemplateResource {

	/**
	 * The empty constructor is required by {@link java.io.Externalizable}. Do
	 * not use this for any other purpose.
	 */
	public DDMTemplateResource() {
	}

	public DDMTemplateResource(String ddmTemplateKey, DDMTemplate ddmTemplate) {
		if (Validator.isNull(ddmTemplateKey)) {
			throw new IllegalArgumentException("DDM Template Key is null");
		}

		if (ddmTemplate == null) {
			throw new IllegalArgumentException("DDM template is null");
		}

		_ddmTemplateKey = ddmTemplateKey;
		_ddmTemplate = ddmTemplate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMTemplateResource)) {
			return false;
		}

		DDMTemplateResource ddmTemplateResource = (DDMTemplateResource)obj;

		if (_ddmTemplateKey.equals(ddmTemplateResource._ddmTemplateKey) &&
			_ddmTemplate.equals(ddmTemplateResource._ddmTemplate)) {

			return true;
		}

		return false;
	}

	@Override
	public long getLastModified() {
		Date modifiedDate = _ddmTemplate.getModifiedDate();

		return modifiedDate.getTime();
	}

	@Override
	public Reader getReader() {
		String script = _ddmTemplate.getScript();

		return new UnsyncStringReader(script);
	}

	@Override
	public String getTemplateId() {
		return _ddmTemplateKey;
	}

	@Override
	public int hashCode() {
		return _ddmTemplateKey.hashCode() * 11 + _ddmTemplate.hashCode();
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		long ddmTemplateId = objectInput.readLong();

		try {
			_ddmTemplate = DDMTemplateLocalServiceUtil.getDDMTemplate(
				ddmTemplateId);
		}
		catch (Exception e) {
			throw new IOException(
				"Unable to retrieve ddm template with ID " + ddmTemplateId, e);
		}

		_ddmTemplateKey = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(_ddmTemplate.getTemplateId());
		objectOutput.writeUTF(_ddmTemplateKey);
	}

	private DDMTemplate _ddmTemplate;
	private String _ddmTemplateKey;

}
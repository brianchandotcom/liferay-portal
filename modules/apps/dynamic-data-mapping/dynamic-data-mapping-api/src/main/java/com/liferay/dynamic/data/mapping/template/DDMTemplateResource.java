/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.template;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.Validator;

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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DDMTemplateResource)) {
			return false;
		}

		DDMTemplateResource ddmTemplateResource = (DDMTemplateResource)object;

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
		return new UnsyncStringReader(_ddmTemplate.getScript());
	}

	@Override
	public String getTemplateId() {
		return _ddmTemplateKey;
	}

	@Override
	public int hashCode() {
		return (_ddmTemplateKey.hashCode() * 11) + _ddmTemplate.hashCode();
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		long ddmTemplateId = objectInput.readLong();

		try {
			_ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
				ddmTemplateId);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to retrieve ddm template with ID " + ddmTemplateId,
				exception);
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
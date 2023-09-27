/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.webdav;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.WebDAVException;

import java.io.InputStream;

/**
 * @author Juan Fernández
 */
public class DDMTemplateResourceImpl extends BaseResourceImpl {

	public DDMTemplateResourceImpl(
		DDMTemplate template, String parentPath, String name) {

		super(
			parentPath, name, template.getName(template.getDefaultLanguageId()),
			template.getCreateDate(), template.getModifiedDate(),
			template.getScript().getBytes().length);

		setModel(template);
		setClassName(DDMTemplate.class.getName());
		setPrimaryKey(template.getPrimaryKey());

		_template = template;
	}

	@Override
	public InputStream getContentAsStream() throws WebDAVException {
		try {
			String script = _template.getScript();

			return new UnsyncByteArrayInputStream(
				script.getBytes(StringPool.UTF8));
		}
		catch (Exception exception) {
			throw new WebDAVException(exception);
		}
	}

	@Override
	public String getContentType() {
		return ContentTypes.TEXT_XML;
	}

	@Override
	public boolean isCollection() {
		return false;
	}

	private final DDMTemplate _template;

}
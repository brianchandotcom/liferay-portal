/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.dynamic.data.mapping.webdav;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
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
			return new UnsyncByteArrayInputStream(
				_template.getScript().getBytes(StringPool.UTF8));
		}
		catch (Exception e) {
			throw new WebDAVException(e);
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
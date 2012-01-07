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

package com.liferay.portlet.journal.webdav;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portlet.journal.model.JournalTemplate;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalTemplateResourceImpl extends BaseResourceImpl {

	public JournalTemplateResourceImpl(
		JournalTemplate template, String parentPath, String name) {

		super(
			parentPath, name, template.getTemplateId(),
			template.getCreateDate(), template.getModifiedDate(),
			template.getXsl().length());

		setModel(template);
		setClassName(JournalTemplate.class.getName());
		setPrimaryKey(template.getPrimaryKey());

		_template = template;
	}

	@Override
	public String getContentType() {
		return ContentTypes.TEXT_XML;
	}

	@Override
	public InputStream getContentAsStream() throws WebDAVException {
		try {
			return new UnsyncByteArrayInputStream(
				_template.getXsl().getBytes(StringPool.UTF8));
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public boolean isCollection() {
		return false;
	}

	private JournalTemplate _template;

}
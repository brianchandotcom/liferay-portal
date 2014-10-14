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

package com.liferay.portlet.dynamicdatamapping.webdav;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.io.InputStream;

/**
 * @author Juan Fernández
 */
public class DDMStructureResourceImpl extends BaseResourceImpl {

	public DDMStructureResourceImpl(
		DDMStructure structure, String parentPath, String name) {

		super(
			parentPath, name,
			structure.getName(structure.getDefaultLanguageId()),
			structure.getCreateDate(), structure.getModifiedDate(),
			structure.getDefinition().length());

		setModel(structure);
		setClassName(DDMStructure.class.getName());
		setPrimaryKey(structure.getPrimaryKey());

		_structure = structure;
	}

	@Override
	public InputStream getContentAsStream() throws WebDAVException {
		try {
			return new UnsyncByteArrayInputStream(
				_structure.getDefinition().getBytes(StringPool.UTF8));
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

	private final DDMStructure _structure;

}
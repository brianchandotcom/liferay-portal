/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.webdav;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.WebDAVException;

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
			structure.getDefinition().getBytes().length);

		setModel(structure);
		setClassName(DDMStructure.class.getName());
		setPrimaryKey(structure.getPrimaryKey());

		_structure = structure;
	}

	@Override
	public InputStream getContentAsStream() throws WebDAVException {
		try {
			String definition = _structure.getDefinition();

			return new UnsyncByteArrayInputStream(
				definition.getBytes(StringPool.UTF8));
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

	private final DDMStructure _structure;

}
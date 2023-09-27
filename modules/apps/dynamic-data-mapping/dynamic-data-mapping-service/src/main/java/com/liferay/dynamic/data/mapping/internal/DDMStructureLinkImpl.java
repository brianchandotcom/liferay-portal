/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal;

import com.liferay.dynamic.data.mapping.kernel.DDMStructureLink;

/**
 * @author Rafael Praxedes
 */
public class DDMStructureLinkImpl implements DDMStructureLink {

	public DDMStructureLinkImpl(
		com.liferay.dynamic.data.mapping.model.DDMStructureLink
			ddmStructureLink) {

		_ddmStructureLink = ddmStructureLink;
	}

	@Override
	public String getClassName() {
		return _ddmStructureLink.getClassName();
	}

	@Override
	public long getClassNameId() {
		return _ddmStructureLink.getClassNameId();
	}

	@Override
	public long getClassPK() {
		return _ddmStructureLink.getClassPK();
	}

	@Override
	public long getStructureId() {
		return _ddmStructureLink.getStructureId();
	}

	@Override
	protected Object clone() {
		return new DDMStructureLinkImpl(
			(com.liferay.dynamic.data.mapping.model.DDMStructureLink)
				_ddmStructureLink.clone());
	}

	private final com.liferay.dynamic.data.mapping.model.DDMStructureLink
		_ddmStructureLink;

}
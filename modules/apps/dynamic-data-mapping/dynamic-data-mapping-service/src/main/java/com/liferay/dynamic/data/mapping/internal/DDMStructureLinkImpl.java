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
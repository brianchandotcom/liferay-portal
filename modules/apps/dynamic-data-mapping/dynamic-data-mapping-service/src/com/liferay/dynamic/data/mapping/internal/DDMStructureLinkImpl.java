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

import com.liferay.portlet.dynamicdatamapping.DDMStructureLink;

/**
 * @author Rafael Praxedes
 */
public class DDMStructureLinkImpl implements DDMStructureLink {

	public DDMStructureLinkImpl(com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink structureLink) {
		_structureLink = structureLink;
	}

	@Override
	public String getClassName() {
		return _structureLink.getClassName();
	}

	@Override
	public long getClassNameId() {
		return _structureLink.getClassNameId();
	}

	@Override
	public long getClassPK() {
		return _structureLink.getClassPK();
	}

	@Override
	public long getStructureId() {
		return _structureLink.getStructureId();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		DDMStructureLinkImpl ddmStructureLinkImpl = new DDMStructureLinkImpl((com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink) _structureLink.clone());
		return ddmStructureLinkImpl;
	}

	private final com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink _structureLink;
}

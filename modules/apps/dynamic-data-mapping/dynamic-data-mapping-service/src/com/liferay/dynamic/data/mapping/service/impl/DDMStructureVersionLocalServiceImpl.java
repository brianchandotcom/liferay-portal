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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureVersionLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.util.comparator.StructureVersionVersionComparator;

import java.util.Collections;
import java.util.List;

/**
 * @author Pablo Carvalho
 */
@ProviderType
public class DDMStructureVersionLocalServiceImpl
	extends DDMStructureVersionLocalServiceBaseImpl {

	@Override
	public DDMStructureVersion getLatestStructureVersion(long structureId)
		throws PortalException {

		List<DDMStructureVersion> structureVersions =
			ddmStructureVersionPersistence.findByStructureId(structureId);

		if (structureVersions.isEmpty()) {
			throw new NoSuchStructureVersionException(
				"No structure versions found for structure ID " + structureId);
		}

		structureVersions = ListUtil.copy(structureVersions);

		Collections.sort(
			structureVersions, new StructureVersionVersionComparator());

		return structureVersions.get(0);
	}

	@Override
	public DDMStructureVersion getStructureVersion(long structureVersionId)
		throws PortalException {

		return ddmStructureVersionPersistence.findByPrimaryKey(
			structureVersionId);
	}

	@Override
	public DDMStructureVersion getStructureVersion(
			long structureId, String version)
		throws PortalException {

		return ddmStructureVersionPersistence.findByS_V(structureId, version);
	}

	@Override
	public List<DDMStructureVersion> getStructureVersions(long structureId) {
		return ddmStructureVersionPersistence.findByStructureId(structureId);
	}

	@Override
	public List<DDMStructureVersion> getStructureVersions(
		long structureId, int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return ddmStructureVersionPersistence.findByStructureId(
			structureId, start, end, orderByComparator);
	}

	@Override
	public int getStructureVersionsCount(long structureId) {
		return ddmStructureVersionPersistence.countByStructureId(structureId);
	}

}
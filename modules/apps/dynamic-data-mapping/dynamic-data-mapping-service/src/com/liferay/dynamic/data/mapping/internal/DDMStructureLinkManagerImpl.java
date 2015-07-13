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
import com.liferay.portlet.dynamicdatamapping.DDMStructureLinkManager;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLinkLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true)
public class DDMStructureLinkManagerImpl implements DDMStructureLinkManager{

	@Override
	public List<DDMStructureLink> getClassNameStructureLinks(long classNameId) {

		List<DDMStructureLink> structureLinks = new ArrayList<>();

		for (com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink structureLink : _ddmStructureLinkLocalService.getClassNameStructureLinks(classNameId)) {
			structureLinks.add( new DDMStructureLinkImpl(structureLink));
		}

		return structureLinks;
	}

	@Override
	public List<DDMStructureLink> getStructureLinks(long classNameId,
			long classPK) {
		
		List<DDMStructureLink> structureLinks = new ArrayList<>();

		for (com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink structureLink : _ddmStructureLinkLocalService.getStructureLinks(classNameId, classPK)) {
			structureLinks.add( new DDMStructureLinkImpl(structureLink));
		}

		return structureLinks;
	}

	@Reference
	protected void setDDMStructureLinkLocalService(DDMStructureLinkLocalService ddmStructureLinkLocalService) {
		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
	}
	
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;
}

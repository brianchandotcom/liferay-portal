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
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLinkManager;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = DDMStructureLinkManager.class)
public class DDMStructureLinkManagerImpl implements DDMStructureLinkManager {

	@Override
	public DDMStructureLink addStructureLink(
		long classNameId, long classPK, long structureId) {

		com.liferay.dynamic.data.mapping.model.DDMStructureLink
			ddmStructureLink = _ddmStructureLinkLocalService.addStructureLink(
				classNameId, classPK, structureId);

		return new DDMStructureLinkImpl(ddmStructureLink);
	}

	@Override
	public void deleteStructureLink(
			long classNameId, long classPK, long structureId)
		throws PortalException {

		_ddmStructureLinkLocalService.deleteStructureLink(
			classNameId, classPK, structureId);
	}

	@Override
	public void deleteStructureLinks(long classNameId, long classPK) {
		_ddmStructureLinkLocalService.deleteStructureLinks(
			classNameId, classPK);
	}

	@Override
	public List<DDMStructureLink> getStructureLinks(long structureId) {
		List<DDMStructureLink> ddmStructureLinks = new ArrayList<>();

		for (com.liferay.dynamic.data.mapping.model.DDMStructureLink
				ddmStructureLink :
					_ddmStructureLinkLocalService.getStructureLinks(
						structureId)) {

			ddmStructureLinks.add(new DDMStructureLinkImpl(ddmStructureLink));
		}

		return ddmStructureLinks;
	}

	@Override
	public List<DDMStructureLink> getStructureLinks(
		long classNameId, long classPK) {

		List<DDMStructureLink> ddmStructureLinks = new ArrayList<>();

		for (com.liferay.dynamic.data.mapping.model.DDMStructureLink
				ddmStructureLink :
					_ddmStructureLinkLocalService.getStructureLinks(
						classNameId, classPK)) {

			ddmStructureLinks.add(new DDMStructureLinkImpl(ddmStructureLink));
		}

		return ddmStructureLinks;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLinkLocalService(
		DDMStructureLinkLocalService ddmStructureLinkLocalService) {

		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
	}

	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

}
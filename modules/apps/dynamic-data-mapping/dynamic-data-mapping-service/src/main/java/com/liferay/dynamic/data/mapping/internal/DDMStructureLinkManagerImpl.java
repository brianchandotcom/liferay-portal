/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = DDMStructureLinkManager.class)
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

	@Reference
	private DDMStructureLinkLocalService _ddmStructureLinkLocalService;

}
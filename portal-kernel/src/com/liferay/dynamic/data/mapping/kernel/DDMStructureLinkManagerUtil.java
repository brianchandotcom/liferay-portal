/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.List;

/**
 * @author Rafael Praxedes
 */
public class DDMStructureLinkManagerUtil {

	public static DDMStructureLink addStructureLink(
		long classNameId, long classPK, long structureId) {

		return _ddmStructureLinkManager.addStructureLink(
			classNameId, classPK, structureId);
	}

	public static void deleteStructureLink(
			long classNameId, long classPK, long structureId)
		throws PortalException {

		_ddmStructureLinkManager.deleteStructureLink(
			classNameId, classPK, structureId);
	}

	public static void deleteStructureLinks(long classNameId, long classPK) {
		_ddmStructureLinkManager.deleteStructureLinks(classNameId, classPK);
	}

	public static List<DDMStructureLink> getStructureLinks(long structureId) {
		return _ddmStructureLinkManager.getStructureLinks(structureId);
	}

	public static List<DDMStructureLink> getStructureLinks(
		long classNameId, long classPK) {

		return _ddmStructureLinkManager.getStructureLinks(classNameId, classPK);
	}

	private static volatile DDMStructureLinkManager _ddmStructureLinkManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			DDMStructureLinkManager.class, DDMStructureLinkManagerUtil.class,
			"_ddmStructureLinkManager", false);

}
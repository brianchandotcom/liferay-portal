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

package com.liferay.document.library.util;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class DLFileEntryTypeUtil {

	public static List<DDMStructure> getDDMStructures(
		DLFileEntryType dlFileEntryType) {

		List<DDMStructureLink> ddmStructureLinks =
			DDMStructureLinkLocalServiceUtil.getStructureLinks(
				PortalUtil.getClassNameId(DLFileEntryType.class),
				dlFileEntryType.getFileEntryTypeId());

		List<DDMStructure> ddmStructures = _getDDMStructures(ddmStructureLinks);

		// See LPS-104152

		if (ListUtil.isEmpty(ddmStructures) ||
			!ListUtil.exists(
				ddmStructures,
				ddmStructure ->
					ddmStructure.getStructureId() ==
						dlFileEntryType.getDataDefinitionId())) {

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchStructure(
					dlFileEntryType.getDataDefinitionId());

			if (ddmStructure != null) {
				ddmStructures.add(0, ddmStructure);
			}
		}

		return ddmStructures;
	}

	private static List<DDMStructure> _getDDMStructures(
		List<DDMStructureLink> ddmStructureLinks) {

		List<DDMStructure> ddmStructures = new ArrayList<>();

		for (DDMStructureLink ddmStructureLink : ddmStructureLinks) {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchStructure(
					ddmStructureLink.getStructureId());

			if (ddmStructure != null) {
				ddmStructures.add(ddmStructure);
			}
		}

		return ddmStructures;
	}

}
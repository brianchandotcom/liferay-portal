/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStorageLinkImpl extends DDMStorageLinkBaseImpl {

	@Override
	public String getStorageType() throws PortalException {
		DDMStructure structure = getStructure();

		return structure.getStorageType();
	}

	@Override
	public DDMStructure getStructure() throws PortalException {
		DDMStructureVersion structureVersion =
			DDMStructureVersionLocalServiceUtil.getStructureVersion(
				getStructureVersionId());

		return DDMStructureLocalServiceUtil.getStructure(
			structureVersion.getStructureId());
	}

}
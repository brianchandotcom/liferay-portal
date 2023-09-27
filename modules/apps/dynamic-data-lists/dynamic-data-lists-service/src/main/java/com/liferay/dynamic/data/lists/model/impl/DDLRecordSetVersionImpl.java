/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model.impl;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Leonardo Barros
 */
public class DDLRecordSetVersionImpl extends DDLRecordSetVersionBaseImpl {

	@Override
	public DDMStructureVersion getDDMStructureVersion() throws PortalException {
		return DDMStructureVersionLocalServiceUtil.getDDMStructureVersion(
			getDDMStructureVersionId());
	}

	@Override
	public DDLRecordSet getRecordSet() throws PortalException {
		return DDLRecordSetLocalServiceUtil.getRecordSet(getRecordSetId());
	}

}
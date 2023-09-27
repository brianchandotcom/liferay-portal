/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.web.internal.asset;

import com.liferay.asset.kernel.model.ClassTypeField;
import com.liferay.asset.model.DDMStructureClassType;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Marcellus Tavares
 */
public class DDLRecordSetClassType extends DDMStructureClassType {

	public DDLRecordSetClassType(
		long classTypeId, String classTypeName, String languageId) {

		super(classTypeId, classTypeName, languageId);
	}

	@Override
	public List<ClassTypeField> getClassTypeFields() throws PortalException {
		DDLRecordSet recordSet = DDLRecordSetServiceUtil.getRecordSet(
			getClassTypeId());

		return getClassTypeFields(recordSet.getDDMStructureId());
	}

}
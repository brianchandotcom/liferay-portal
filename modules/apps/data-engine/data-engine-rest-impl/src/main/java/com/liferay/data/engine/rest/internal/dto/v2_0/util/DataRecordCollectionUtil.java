/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;

/**
 * @author Jeyvison Nascimento
 */
public class DataRecordCollectionUtil {

	public static DataRecordCollection toDataRecordCollection(
		DDLRecordSet ddlRecordSet) {

		if (ddlRecordSet == null) {
			return new DataRecordCollection();
		}

		return new DataRecordCollection() {
			{
				dataDefinitionId = ddlRecordSet.getDDMStructureId();
				dataRecordCollectionKey = ddlRecordSet.getRecordSetKey();
				description = LocalizedValueUtil.toStringObjectMap(
					ddlRecordSet.getDescriptionMap());
				id = ddlRecordSet.getRecordSetId();
				name = LocalizedValueUtil.toStringObjectMap(
					ddlRecordSet.getNameMap());
				siteId = ddlRecordSet.getGroupId();
			}
		};
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class InstanceEntityModel implements EntityModel {

	public InstanceEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new StringEntityField(
				"assetType",
				locale -> Field.getSortableFieldName(
					"assetType_".concat(LocaleUtil.toLanguageId(locale)))),
			new StringEntityField("assigneeName", locale -> "assigneeName"),
			new DateTimeEntityField(
				"dateCreated", locale -> "createDate", locale -> "createDate"),
			new DateTimeEntityField(
				"dateOverdue", locale -> "overdueDate",
				locale -> "overdueDate"),
			new StringEntityField("userName", locale -> "userName"));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}
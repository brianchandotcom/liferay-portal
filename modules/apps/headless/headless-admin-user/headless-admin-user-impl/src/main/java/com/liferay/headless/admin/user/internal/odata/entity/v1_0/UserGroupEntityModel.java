/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.odata.entity.v1_0;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Erick Monteiro
 */
public class UserGroupEntityModel implements EntityModel {

	public UserGroupEntityModel() {
		_entityFieldsMap = EntityModel.toEntityFieldsMap(
			new IdEntityField(
				"companyId", locale -> Field.COMPANY_ID, String::valueOf),
			new StringEntityField("description", locale -> Field.DESCRIPTION),
			new StringEntityField(
				"name", locale -> Field.getSortableFieldName(Field.NAME)),
			new IdEntityField(
				"userGroupId", locale -> Field.USER_GROUP_ID, String::valueOf));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}
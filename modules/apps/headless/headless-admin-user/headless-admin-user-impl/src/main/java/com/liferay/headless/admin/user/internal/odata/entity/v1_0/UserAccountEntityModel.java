/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.odata.entity.v1_0;

import com.liferay.headless.common.spi.odata.entity.EntityFieldsMapFactory;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.CollectionEntityField;
import com.liferay.portal.odata.entity.DateEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class UserAccountEntityModel implements EntityModel {

	public UserAccountEntityModel() {
		_entityFieldsMap = EntityFieldsMapFactory.create(
			new CollectionEntityField(
				new StringEntityField(
					"keywords", locale -> "assetTagNames.raw")),
			new CollectionEntityField(
				new StringEntityField("roleNames", locale -> "roleNames")),
			new CollectionEntityField(
				new StringEntityField(
					"userGroupRoleNames", locale -> "userGroupRoleNames")),
			new DateEntityField(
				"birthDate", locale -> Field.getSortableFieldName("birthDate"),
				locale -> "birthDate"),
			new DateTimeEntityField(
				"dateCreated",
				locale -> Field.getSortableFieldName(Field.CREATE_DATE),
				locale -> Field.CREATE_DATE),
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new DateTimeEntityField(
				"lastLoginDate",
				locale -> Field.getSortableFieldName("lastLoginDate"),
				locale -> "lastLoginDate"),
			new IdEntityField(
				"organizationIds", locale -> "organizationIds",
				String::valueOf),
			new IdEntityField("roleIds", locale -> "roleIds", String::valueOf),
			new IdEntityField(
				"userGroupIds", locale -> "userGroupIds", String::valueOf),
			new StringEntityField(
				"alternateName",
				locale -> Field.getSortableFieldName("screenName")),
			new StringEntityField("emailAddress", locale -> "emailAddress"),
			new StringEntityField(
				"familyName", locale -> Field.getSortableFieldName("lastName")),
			new StringEntityField(
				"givenName", locale -> Field.getSortableFieldName("firstName")),
			new StringEntityField(
				"jobTitle", locale -> Field.getSortableFieldName("jobTitle")),
			new StringEntityField("name", locale -> Field.USER_NAME));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}
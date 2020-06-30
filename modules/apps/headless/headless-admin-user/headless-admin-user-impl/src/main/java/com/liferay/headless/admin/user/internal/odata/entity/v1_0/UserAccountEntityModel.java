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

package com.liferay.headless.admin.user.internal.odata.entity.v1_0;

import com.liferay.headless.common.spi.odata.entity.EntityFieldsMapFactory;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.CollectionEntityField;
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
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
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
				"jobTitle", locale -> Field.getSortableFieldName("jobTitle")));
	}

	@Override
	public Map<String, EntityField> getEntityFieldsMap() {
		return _entityFieldsMap;
	}

	private final Map<String, EntityField> _entityFieldsMap;

}
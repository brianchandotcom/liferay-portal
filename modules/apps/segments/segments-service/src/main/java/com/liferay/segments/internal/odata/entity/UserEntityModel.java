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

package com.liferay.segments.internal.odata.entity;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.DateEntityField;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the entity data model from the User.
 *
 * @author David Arques
 */
@Component(
	property = "entity.model.name=" + UserEntityModel.NAME,
	service = EntityModel.class
)
public class UserEntityModel extends BaseExpandoEntityModel {

	public static final String NAME = "User";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected EntityField[] getEntityFields() {
		return new EntityField[] {
			new DateEntityField(
				"birthDate", locale -> Field.getSortableFieldName("birthDate"),
				locale -> "birthDate"),
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new IdEntityField(
				"ancestorOrganizationIds", locale -> "ancestorOrganizationIds",
				String::valueOf),
			new IdEntityField(
				"assetCategoryIds", locale -> Field.ASSET_CATEGORY_IDS,
				String::valueOf),
			new IdEntityField(
				"assetTagIds", locale -> Field.ASSET_TAG_IDS, String::valueOf),
			new IdEntityField(
				"classPK", locale -> Field.USER_ID, String::valueOf),
			new IdEntityField(
				"companyId", locale -> Field.COMPANY_ID, String::valueOf),
			new IdEntityField(
				"groupId", locale -> Field.GROUP_ID, String::valueOf),
			new IdEntityField(
				"groupIds", locale -> "groupIds", String::valueOf),
			new IdEntityField(
				"organizationIds", locale -> "organizationIds",
				String::valueOf),
			new IdEntityField("roleIds", locale -> "roleIds", String::valueOf),
			new IdEntityField(
				"scopeGroupId", locale -> "scopeGroupId", String::valueOf),
			new IdEntityField(
				"segmentsEntryIds", locale -> "segmentsEntryIds",
				String::valueOf),
			new IdEntityField("teamIds", locale -> "teamIds", String::valueOf),
			new IdEntityField(
				"userGroupIds", locale -> "userGroupIds", String::valueOf),
			new IdEntityField(
				"userGroupRoleIds", locale -> "userGroupRoleIds",
				String::valueOf),
			new IdEntityField(
				"userId", locale -> Field.USER_ID, String::valueOf),
			new StringEntityField("emailAddress", locale -> "emailAddress"),
			new StringEntityField(
				"firstName", locale -> Field.getSortableFieldName("firstName")),
			new StringEntityField(
				"jobTitle", locale -> Field.getSortableFieldName("jobTitle")),
			new StringEntityField(
				"lastName", locale -> Field.getSortableFieldName("lastName")),
			new StringEntityField(
				"screenName",
				locale -> Field.getSortableFieldName("screenName")),
			new StringEntityField("userName", locale -> Field.USER_NAME)
		};
	}

	@Override
	protected String getModelClassName() {
		return User.class.getName();
	}

}
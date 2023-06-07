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

import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.odata.entity.DateTimeEntityField;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.IdEntityField;
import com.liferay.portal.odata.entity.StringEntityField;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the entity data model from the Organization.
 *
 * @author David Arques
 */
@Component(
	property = "entity.model.name=" + OrganizationEntityModel.NAME,
	service = EntityModel.class
)
public class OrganizationEntityModel extends BaseExpandoEntityModel {

	public static final String NAME = "Organization";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	protected EntityField[] getEntityFields() {
		return new EntityField[] {
			new DateTimeEntityField(
				"dateModified",
				locale -> Field.getSortableFieldName(Field.MODIFIED_DATE),
				locale -> Field.MODIFIED_DATE),
			new IdEntityField(
				"assetCategoryIds", locale -> Field.ASSET_CATEGORY_IDS,
				String::valueOf),
			new IdEntityField(
				"assetTagIds", locale -> Field.ASSET_TAG_IDS, String::valueOf),
			new IdEntityField(
				"classPK", locale -> Field.ORGANIZATION_ID, String::valueOf),
			new IdEntityField(
				"companyId", locale -> Field.COMPANY_ID, String::valueOf),
			new IdEntityField(
				"organizationId", locale -> Field.ORGANIZATION_ID,
				String::valueOf),
			new IdEntityField(
				"parentOrganizationId", locale -> "parentOrganizationId",
				String::valueOf),
			new StringEntityField("country", locale -> "country"),
			new StringEntityField(
				"name", locale -> Field.getSortableFieldName(Field.NAME)),
			new StringEntityField(
				"nameTreePath",
				locale -> Field.getSortableFieldName("nameTreePath_String")),
			new StringEntityField(
				"region", locale -> Field.getSortableFieldName("region")),
			new StringEntityField("type", locale -> Field.TYPE)
		};
	}

	@Override
	protected String getModelClassName() {
		return Organization.class.getName();
	}

}
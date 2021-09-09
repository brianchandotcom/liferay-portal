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

package com.liferay.object.admin.rest.internal.resource.v1_0;

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.object.admin.rest.internal.dto.v1_0.util.ObjectFieldUtil;
import com.liferay.object.admin.rest.resource.v1_0.ObjectFieldResource;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/object-field.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, ObjectFieldResource.class}
)
public class ObjectFieldResourceImpl
	extends BaseObjectFieldResourceImpl implements NestedFieldSupport {

	@NestedField(parentClass = ObjectDefinition.class, value = "objectFields")
	@Override
	public Page<ObjectField> getObjectDefinitionObjectFieldsPage(
			Long objectDefinitionId, String search, Pagination pagination)
		throws Exception {

		return SearchUtil.search(
			Collections.emptyMap(),
			booleanQuery -> {
			},
			null, com.liferay.object.model.ObjectField.class.getName(), search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setAttribute(Field.NAME, search);
				searchContext.setAttribute(
					"objectDefinitionId", objectDefinitionId);
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			null,
			document -> ObjectFieldUtil.toObjectField(
				_objectFieldLocalService.getObjectField(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	@Override
	public ObjectField getObjectField(Long objectFieldId) throws Exception {
		return ObjectFieldUtil.toObjectField(
			_objectFieldLocalService.getObjectField(objectFieldId));
	}

	@Override
	public ObjectField postObjectDefinitionObjectField(
			Long objectDefinitionId, ObjectField objectField)
		throws Exception {

		return ObjectFieldUtil.toObjectField(
			_objectFieldLocalService.addCustomObjectField(
				contextUser.getUserId(), objectField.getListTypeDefinitionId(),
				objectDefinitionId, objectField.getIndexed(),
				objectField.getIndexedAsKeyword(),
				objectField.getIndexedLanguageId(),
				LocalizedMapUtil.getLocalizedMap(objectField.getLabel()),
				objectField.getName(), objectField.getRequired(),
				objectField.getTypeAsString()));
	}

	@Override
	public ObjectField putObjectField(
			Long objectFieldId, ObjectField objectField)
		throws Exception {

		return ObjectFieldUtil.toObjectField(
			_objectFieldLocalService.updateCustomObjectField(
				objectFieldId, objectField.getListTypeDefinitionId(),
				objectField.getIndexed(), objectField.getIndexedAsKeyword(),
				objectField.getIndexedLanguageId(),
				LocalizedMapUtil.getLocalizedMap(objectField.getLabel()),
				objectField.getName(), objectField.getRequired(),
				objectField.getTypeAsString()));
	}

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}
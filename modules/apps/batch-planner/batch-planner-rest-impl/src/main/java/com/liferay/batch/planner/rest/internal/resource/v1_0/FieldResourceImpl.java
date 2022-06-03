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

package com.liferay.batch.planner.rest.internal.resource.v1_0;

import com.liferay.batch.planner.rest.dto.v1_0.Field;
import com.liferay.batch.planner.rest.internal.helper.FieldHelper;
import com.liferay.batch.planner.rest.resource.v1_0.FieldResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Comparator;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Matija Petanjek
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/field.properties",
	scope = ServiceScope.PROTOTYPE, service = FieldResource.class
)
public class FieldResourceImpl extends BaseFieldResourceImpl {

	@Override
	public Page<Field> getPlanInternalClassNameFieldsPage(
			String internalClassName, Boolean export)
		throws Exception {

		List<com.liferay.portal.vulcan.batch.engine.Field> fields =
			_fieldHelper.getFields(internalClassName);

		if (GetterUtil.getBoolean(export)) {
			fields = _fieldHelper.filter(
				fields,
				com.liferay.portal.vulcan.batch.engine.Field.AccessType.WRITE);
		}
		else {
			fields = _fieldHelper.filter(
				fields,
				com.liferay.portal.vulcan.batch.engine.Field.AccessType.READ);
		}

		fields.sort(Comparator.comparing(field -> field.getName()));

		return Page.of(transform(fields, this::_toField));
	}

	private Field _toField(com.liferay.portal.vulcan.batch.engine.Field field) {
		return new Field() {
			{
				description = field.getDescription();
				name = field.getName();
				required = field.isRequired();
				type = field.getType();
			}
		};
	}

	@Reference
	private FieldHelper _fieldHelper;

}
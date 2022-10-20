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
import com.liferay.batch.planner.rest.internal.vulcan.batch.engine.FieldProvider;
import com.liferay.batch.planner.rest.resource.v1_0.FieldResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
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

		List<com.liferay.portal.vulcan.batch.engine.Field> vulcanFields =
			_getVulcanFields(internalClassName);

		if (GetterUtil.getBoolean(export)) {
			vulcanFields = _fieldProvider.filter(
				vulcanFields,
				com.liferay.portal.vulcan.batch.engine.Field.AccessType.WRITE);
		}
		else {
			vulcanFields = _fieldProvider.filter(
				vulcanFields,
				com.liferay.portal.vulcan.batch.engine.Field.AccessType.READ);
		}

		vulcanFields.sort(Comparator.comparing(field -> field.getName()));

		return Page.of(transform(vulcanFields, this::_toField));
	}

	private List<com.liferay.portal.vulcan.batch.engine.Field> _getVulcanFields(
			String internalClassName)
		throws Exception {

		int idx = internalClassName.indexOf(StringPool.POUND);

		if (idx < 0) {
			return _fieldProvider.getFields(internalClassName);
		}

		String objectDefinitionName = StringUtil.replaceLast(
			internalClassName.substring(idx + 1),
			String.valueOf(contextCompany.getCompanyId()), "");

		return _fieldProvider.getFields(
			contextCompany.getCompanyId(), objectDefinitionName,
			contextUriInfo);
	}

	private Field _toField(
		com.liferay.portal.vulcan.batch.engine.Field vulcanField) {

		return new Field() {
			{
				description = vulcanField.getDescription();
				name = vulcanField.getName();
				required = vulcanField.isRequired();
				type = vulcanField.getType();
			}
		};
	}

	@Reference
	private FieldProvider _fieldProvider;

}
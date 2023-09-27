/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.rest.internal.resource.v1_0;

import com.liferay.batch.planner.rest.dto.v1_0.Field;
import com.liferay.batch.planner.rest.internal.vulcan.batch.engine.FieldProvider;
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
	public Page<Field> getPlanInternalClassNameKeyFieldsPage(
			String internalClassNameKey, Boolean export)
		throws Exception {

		List<com.liferay.portal.vulcan.batch.engine.Field> vulcanFields =
			_fieldProvider.getFields(
				contextCompany.getCompanyId(), internalClassNameKey,
				contextUriInfo);

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
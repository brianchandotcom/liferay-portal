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

package com.liferay.dynamic.data.mapping.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMTemplateTable;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersionTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateVersionPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMTemplateVersionTableReferenceDefinition
	implements TableReferenceDefinition<DDMTemplateVersionTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<DDMTemplateVersionTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			DDMTemplateVersionTable.INSTANCE.groupId,
			GroupTable.INSTANCE.groupId
		).singleColumnReference(
			DDMTemplateVersionTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			DDMTemplateVersionTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DDMTemplateVersionTable.INSTANCE.userName,
			DDMTemplateVersionTable.INSTANCE.createDate
		).nonreferenceColumns(
			DDMTemplateVersionTable.INSTANCE.classNameId,
			DDMTemplateVersionTable.INSTANCE.classPK
		).singleColumnReference(
			DDMTemplateVersionTable.INSTANCE.templateId,
			DDMTemplateTable.INSTANCE.templateId
		).nonreferenceColumns(
			DDMTemplateVersionTable.INSTANCE.version,
			DDMTemplateVersionTable.INSTANCE.name,
			DDMTemplateVersionTable.INSTANCE.description,
			DDMTemplateVersionTable.INSTANCE.language,
			DDMTemplateVersionTable.INSTANCE.script,
			DDMTemplateVersionTable.INSTANCE.status
		).singleColumnReference(
			DDMTemplateVersionTable.INSTANCE.statusByUserId,
			UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DDMTemplateVersionTable.INSTANCE.statusByUserName,
			DDMTemplateVersionTable.INSTANCE.statusDate
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmTemplateVersionPersistence;
	}

	@Override
	public DDMTemplateVersionTable getTable() {
		return DDMTemplateVersionTable.INSTANCE;
	}

	@Reference
	private DDMTemplateVersionPersistence _ddmTemplateVersionPersistence;

}
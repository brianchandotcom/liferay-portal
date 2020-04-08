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
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
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
		TableReferenceDefinitionHelper<DDMTemplateVersionTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				DDMTemplateVersionTable.INSTANCE,
				DDMTemplateVersionTable.INSTANCE.groupId.eq(
					GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				DDMTemplateVersionTable.INSTANCE,
				DDMTemplateVersionTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				DDMTemplateVersionTable.INSTANCE,
				DDMTemplateVersionTable.INSTANCE.userId.eq(
					UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.classNameId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.classPK);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				DDMTemplateTable.INSTANCE
			).innerJoinON(
				DDMTemplateVersionTable.INSTANCE,
				DDMTemplateVersionTable.INSTANCE.templateId.eq(
					DDMTemplateTable.INSTANCE.templateId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.version);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.language);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.script);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.status);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				DDMTemplateVersionTable.INSTANCE,
				DDMTemplateVersionTable.INSTANCE.statusByUserId.eq(
					UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.statusByUserName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateVersionTable.INSTANCE.statusDate);
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
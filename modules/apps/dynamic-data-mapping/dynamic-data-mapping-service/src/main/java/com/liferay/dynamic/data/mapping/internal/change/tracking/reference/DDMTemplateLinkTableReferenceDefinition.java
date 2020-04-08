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
import com.liferay.dynamic.data.mapping.model.DDMTemplateLinkTable;
import com.liferay.dynamic.data.mapping.model.DDMTemplateTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateLinkPersistence;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMTemplateLinkTableReferenceDefinition
	implements TableReferenceDefinition<DDMTemplateLinkTable> {

	@Override
	public void defineTableReferences(
		TableReferenceDefinitionHelper<DDMTemplateLinkTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				DDMTemplateLinkTable.INSTANCE,
				DDMTemplateLinkTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateLinkTable.INSTANCE.classNameId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			DDMTemplateLinkTable.INSTANCE.classPK);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				DDMTemplateTable.INSTANCE
			).innerJoinON(
				DDMTemplateLinkTable.INSTANCE,
				DDMTemplateLinkTable.INSTANCE.templateId.eq(
					DDMTemplateTable.INSTANCE.templateId)
			));
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmTemplateLinkPersistence;
	}

	@Override
	public DDMTemplateLinkTable getTable() {
		return DDMTemplateLinkTable.INSTANCE;
	}

	@Reference
	private DDMTemplateLinkPersistence _ddmTemplateLinkPersistence;

}
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

package com.liferay.template.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMTemplateTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.template.model.TemplateEntryTable;
import com.liferay.template.service.persistence.TemplateEntryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = TableReferenceDefinition.class)
public class TemplateEntryTableReferenceDefinition
	implements TableReferenceDefinition<TemplateEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<TemplateEntryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<TemplateEntryTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			TemplateEntryTable.INSTANCE
		).singleColumnReference(
			TemplateEntryTable.INSTANCE.ddmTemplateId,
			DDMTemplateTable.INSTANCE.templateId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _templateEntryPersistence;
	}

	@Override
	public TemplateEntryTable getTable() {
		return TemplateEntryTable.INSTANCE;
	}

	@Reference
	private TemplateEntryPersistence _templateEntryPersistence;

}
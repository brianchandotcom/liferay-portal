/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
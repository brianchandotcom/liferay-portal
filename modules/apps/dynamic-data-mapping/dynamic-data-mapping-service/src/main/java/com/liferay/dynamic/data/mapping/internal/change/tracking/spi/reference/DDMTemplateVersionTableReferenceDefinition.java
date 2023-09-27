/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMTemplateTable;
import com.liferay.dynamic.data.mapping.model.DDMTemplateVersionTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplateVersionPersistence;
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
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDMTemplateVersionTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDMTemplateVersionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DDMTemplateVersionTable.INSTANCE
		).singleColumnReference(
			DDMTemplateVersionTable.INSTANCE.templateId,
			DDMTemplateTable.INSTANCE.templateId
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
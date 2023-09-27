/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPDefinitionOptionRelTable;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRelTable;
import com.liferay.commerce.product.service.persistence.CPDefinitionOptionValueRelPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CPDefinitionOptionValueRelTableReferenceDefinition
	implements TableReferenceDefinition<CPDefinitionOptionValueRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPDefinitionOptionValueRelTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPDefinitionOptionValueRelTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CPDefinitionOptionValueRelTable.INSTANCE
		).singleColumnReference(
			CPDefinitionOptionValueRelTable.INSTANCE.CPDefinitionOptionRelId,
			CPDefinitionOptionRelTable.INSTANCE.CPDefinitionOptionRelId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpDefinitionOptionValueRelPersistence;
	}

	@Override
	public CPDefinitionOptionValueRelTable getTable() {
		return CPDefinitionOptionValueRelTable.INSTANCE;
	}

	@Reference
	private CPDefinitionOptionValueRelPersistence
		_cpDefinitionOptionValueRelPersistence;

}
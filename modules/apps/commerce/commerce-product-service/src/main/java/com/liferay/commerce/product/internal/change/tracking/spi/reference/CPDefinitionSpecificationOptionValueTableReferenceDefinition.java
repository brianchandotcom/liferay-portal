/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValueTable;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.CPSpecificationOptionTable;
import com.liferay.commerce.product.service.persistence.CPDefinitionSpecificationOptionValuePersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CPDefinitionSpecificationOptionValueTableReferenceDefinition
	implements TableReferenceDefinition
		<CPDefinitionSpecificationOptionValueTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder
			<CPDefinitionSpecificationOptionValueTable>
				childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder
			<CPDefinitionSpecificationOptionValueTable>
				parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CPDefinitionSpecificationOptionValueTable.INSTANCE
		).singleColumnReference(
			CPDefinitionSpecificationOptionValueTable.INSTANCE.CPDefinitionId,
			CPDefinitionTable.INSTANCE.CPDefinitionId
		).singleColumnReference(
			CPDefinitionSpecificationOptionValueTable.INSTANCE.
				CPSpecificationOptionId,
			CPSpecificationOptionTable.INSTANCE.CPSpecificationOptionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpDefinitionSpecificationOptionValuePersistence;
	}

	@Override
	public CPDefinitionSpecificationOptionValueTable getTable() {
		return CPDefinitionSpecificationOptionValueTable.INSTANCE;
	}

	@Reference
	private CPDefinitionSpecificationOptionValuePersistence
		_cpDefinitionSpecificationOptionValuePersistence;

}
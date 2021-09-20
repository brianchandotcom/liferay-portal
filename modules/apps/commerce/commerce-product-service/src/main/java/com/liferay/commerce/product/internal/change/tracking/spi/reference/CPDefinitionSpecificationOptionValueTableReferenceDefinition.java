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
@Component(enabled = false, service = TableReferenceDefinition.class)
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
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
import com.liferay.commerce.product.model.CPDefinitionOptionRelTable;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRelTable;
import com.liferay.commerce.product.service.persistence.CPDefinitionOptionValueRelPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(enabled = false, service = TableReferenceDefinition.class)
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
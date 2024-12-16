/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPSpecificationOptionListTypeDefinitionRelTable;
import com.liferay.commerce.product.model.CPSpecificationOptionTable;
import com.liferay.commerce.product.service.persistence.CPSpecificationOptionListTypeDefinitionRelPersistence;
import com.liferay.list.type.model.ListTypeDefinitionTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CPSOListTypeDefinitionRelTableReferenceDefinition
	implements TableReferenceDefinition
		<CPSpecificationOptionListTypeDefinitionRelTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder
			<CPSpecificationOptionListTypeDefinitionRelTable>
				childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder
			<CPSpecificationOptionListTypeDefinitionRelTable>
				parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CPSpecificationOptionListTypeDefinitionRelTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			CPSpecificationOptionListTypeDefinitionRelTable.INSTANCE.
				CPSpecificationOptionId,
			CPSpecificationOptionTable.INSTANCE.CPSpecificationOptionId
		).singleColumnReference(
			CPSpecificationOptionListTypeDefinitionRelTable.INSTANCE.
				listTypeDefinitionId,
			ListTypeDefinitionTable.INSTANCE.listTypeDefinitionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpSpecificationOptionListTypeDefinitionRelPersistence;
	}

	@Override
	public CPSpecificationOptionListTypeDefinitionRelTable getTable() {
		return CPSpecificationOptionListTypeDefinitionRelTable.INSTANCE;
	}

	@Reference
	private CPSpecificationOptionListTypeDefinitionRelPersistence
		_cpSpecificationOptionListTypeDefinitionRelPersistence;

}
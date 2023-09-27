/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.CPDisplayLayoutTable;
import com.liferay.commerce.product.service.persistence.CPDisplayLayoutPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(service = TableReferenceDefinition.class)
public class CPDisplayLayoutTableReferenceDefinition
	implements TableReferenceDefinition<CPDisplayLayoutTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPDisplayLayoutTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPDisplayLayoutTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.classNameReference(
			CPDisplayLayoutTable.INSTANCE.classPK,
			CPDefinitionTable.INSTANCE.CPDefinitionId, CPDefinition.class
		).groupedModel(
			CPDisplayLayoutTable.INSTANCE
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpDisplayLayoutPersistence;
	}

	@Override
	public CPDisplayLayoutTable getTable() {
		return CPDisplayLayoutTable.INSTANCE;
	}

	@Reference
	private CPDisplayLayoutPersistence _cpDisplayLayoutPersistence;

}
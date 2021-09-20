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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLocalizationTable;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.CPInstanceTable;
import com.liferay.commerce.product.model.CProductTable;
import com.liferay.commerce.product.service.persistence.CPDefinitionPersistence;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(enabled = false, service = TableReferenceDefinition.class)
public class CPDefinitionTableReferenceDefinition
	implements TableReferenceDefinition<CPDefinitionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CPDefinitionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.assetEntryReference(
			CPDefinitionTable.INSTANCE.CPDefinitionId, CPDefinition.class
		).classNameReference(
			CPDefinitionTable.INSTANCE.CPDefinitionId,
			FriendlyURLEntryTable.INSTANCE.classPK, CPDefinition.class
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				FriendlyURLEntryTable.INSTANCE
			).innerJoinON(
				CPDefinitionTable.INSTANCE,
				CPDefinitionTable.INSTANCE.groupId.eq(
					FriendlyURLEntryTable.INSTANCE.groupId)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					CPDefinition.class.getName()
				).and(
					FriendlyURLEntryTable.INSTANCE.classNameId.eq(
						ClassNameTable.INSTANCE.classNameId)
				)
			)
		).singleColumnReference(
			CPDefinitionTable.INSTANCE.CProductId,
			CProductTable.INSTANCE.CProductId
		).singleColumnReference(
			CPDefinitionTable.INSTANCE.CPDefinitionId,
			CPDefinitionLocalizationTable.INSTANCE.CPDefinitionId
		).singleColumnReference(
			CPDefinitionTable.INSTANCE.CPDefinitionId,
			CPInstanceTable.INSTANCE.CPDefinitionId
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CPDefinitionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			CPDefinitionTable.INSTANCE
		).singleColumnReference(
			CPDefinitionTable.INSTANCE.CProductId,
			CProductTable.INSTANCE.CProductId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _cpDefinitionPersistence;
	}

	@Override
	public CPDefinitionTable getTable() {
		return CPDefinitionTable.INSTANCE;
	}

	@Reference
	private CPDefinitionPersistence _cpDefinitionPersistence;

}
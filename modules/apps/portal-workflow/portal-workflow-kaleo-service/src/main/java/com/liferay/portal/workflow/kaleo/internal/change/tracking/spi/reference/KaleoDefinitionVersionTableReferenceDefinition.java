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

package com.liferay.portal.workflow.kaleo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionTable;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersionTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoDefinitionVersionPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoDefinitionVersionTableReferenceDefinition
	implements TableReferenceDefinition<KaleoDefinitionVersionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoDefinitionVersionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			KaleoDefinitionVersionTable.INSTANCE.kaleoDefinitionVersionId,
			KaleoDefinitionVersion.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoDefinitionVersionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoDefinitionVersionTable.INSTANCE
		).singleColumnReference(
			KaleoDefinitionVersionTable.INSTANCE.kaleoDefinitionId,
			KaleoDefinitionTable.INSTANCE.kaleoDefinitionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoDefinitionVersionPersistence;
	}

	@Override
	public KaleoDefinitionVersionTable getTable() {
		return KaleoDefinitionVersionTable.INSTANCE;
	}

	@Reference
	private KaleoDefinitionVersionPersistence
		_kaleoDefinitionVersionPersistence;

}
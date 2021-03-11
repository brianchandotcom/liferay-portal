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
import com.liferay.portal.workflow.kaleo.model.KaleoConditionTable;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoConditionPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoConditionTableReferenceDefinition
	implements TableReferenceDefinition<KaleoConditionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoConditionTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoConditionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoConditionTable.INSTANCE
		).singleColumnReference(
			KaleoConditionTable.INSTANCE.kaleoNodeId,
			KaleoNodeTable.INSTANCE.kaleoNodeId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoConditionPersistence;
	}

	@Override
	public KaleoConditionTable getTable() {
		return KaleoConditionTable.INSTANCE;
	}

	@Reference
	private KaleoConditionPersistence _kaleoConditionPersistence;

}
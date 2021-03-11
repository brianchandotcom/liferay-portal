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
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersionTable;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceTable;
import com.liferay.portal.workflow.kaleo.model.KaleoLogTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstancePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoInstanceTableReferenceDefinition
	implements TableReferenceDefinition<KaleoInstanceTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoInstanceTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			KaleoInstanceTable.INSTANCE.kaleoInstanceId,
			KaleoLogTable.INSTANCE.kaleoInstanceId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoInstanceTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoInstanceTable.INSTANCE
		).singleColumnReference(
			KaleoInstanceTable.INSTANCE.kaleoDefinitionVersionId,
			KaleoDefinitionVersionTable.INSTANCE.kaleoDefinitionVersionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoInstancePersistence;
	}

	@Override
	public KaleoInstanceTable getTable() {
		return KaleoInstanceTable.INSTANCE;
	}

	@Reference
	private KaleoInstancePersistence _kaleoInstancePersistence;

}
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
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstanceTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskFormInstancePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoTaskFormInstanceTableReferenceDefinition
	implements TableReferenceDefinition<KaleoTaskFormInstanceTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoTaskFormInstanceTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoTaskFormInstanceTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoTaskFormInstanceTable.INSTANCE
		).singleColumnReference(
			KaleoTaskFormInstanceTable.INSTANCE.kaleoTaskInstanceTokenId,
			KaleoTaskInstanceTokenTable.INSTANCE.kaleoTaskInstanceTokenId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoTaskFormInstancePersistence;
	}

	@Override
	public KaleoTaskFormInstanceTable getTable() {
		return KaleoTaskFormInstanceTable.INSTANCE;
	}

	@Reference
	private KaleoTaskFormInstancePersistence _kaleoTaskFormInstancePersistence;

}
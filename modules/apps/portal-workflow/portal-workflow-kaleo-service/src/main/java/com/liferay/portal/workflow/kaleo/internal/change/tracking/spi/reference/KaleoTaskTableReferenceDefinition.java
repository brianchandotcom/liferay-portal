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
import com.liferay.portal.workflow.kaleo.model.KaleoNodeTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoTaskTableReferenceDefinition
	implements TableReferenceDefinition<KaleoTaskTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoTaskTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			KaleoTaskTable.INSTANCE.kaleoTaskId,
			KaleoTaskFormTable.INSTANCE.kaleoTaskId
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				KaleoTaskAssignmentTable.INSTANCE
			).innerJoinON(
				KaleoTaskTable.INSTANCE,
				KaleoTaskTable.INSTANCE.kaleoTaskId.eq(
					KaleoTaskAssignmentTable.INSTANCE.kaleoClassPK
				).and(
					KaleoTaskAssignmentTable.INSTANCE.kaleoClassName.eq(
						KaleoTask.class.getName())
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoTaskTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoTaskTable.INSTANCE
		).singleColumnReference(
			KaleoTaskTable.INSTANCE.kaleoNodeId,
			KaleoNodeTable.INSTANCE.kaleoNodeId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoTaskPersistence;
	}

	@Override
	public KaleoTaskTable getTable() {
		return KaleoTaskTable.INSTANCE;
	}

	@Reference
	private KaleoTaskPersistence _kaleoTaskPersistence;

}
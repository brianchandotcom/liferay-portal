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
import com.liferay.portal.workflow.kaleo.model.KaleoTimerTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTransitionTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTransitionPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoTransitionTableReferenceDefinition
	implements TableReferenceDefinition<KaleoTransitionTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoTransitionTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				KaleoTimerTable.INSTANCE
			).innerJoinON(
				KaleoTransitionTable.INSTANCE,
				KaleoTransitionTable.INSTANCE.kaleoTransitionId.eq(
					KaleoTimerTable.INSTANCE.kaleoClassPK
				).and(
					KaleoTimerTable.INSTANCE.kaleoClassName.eq(
						KaleoTask.class.getName())
				)
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoTransitionTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoTransitionTable.INSTANCE
		).singleColumnReference(
			KaleoTransitionTable.INSTANCE.kaleoNodeId,
			KaleoNodeTable.INSTANCE.kaleoNodeId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoTransitionPersistence;
	}

	@Override
	public KaleoTransitionTable getTable() {
		return KaleoTransitionTable.INSTANCE;
	}

	@Reference
	private KaleoTransitionPersistence _kaleoTransitionPersistence;

}
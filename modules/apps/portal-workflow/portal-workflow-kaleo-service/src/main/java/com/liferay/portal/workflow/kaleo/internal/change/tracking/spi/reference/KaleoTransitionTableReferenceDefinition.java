/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
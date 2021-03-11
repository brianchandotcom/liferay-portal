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
import com.liferay.portal.workflow.kaleo.model.KaleoActionTable;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersionTable;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeTable;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNodePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoNodeTableReferenceDefinition
	implements TableReferenceDefinition<KaleoNodeTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoNodeTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				KaleoActionTable.INSTANCE
			).innerJoinON(
				KaleoNodeTable.INSTANCE,
				KaleoNodeTable.INSTANCE.kaleoNodeId.eq(
					KaleoActionTable.INSTANCE.kaleoClassPK
				).and(
					KaleoActionTable.INSTANCE.kaleoClassName.eq(
						KaleoNode.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				KaleoNotificationTable.INSTANCE
			).innerJoinON(
				KaleoNodeTable.INSTANCE,
				KaleoNodeTable.INSTANCE.kaleoNodeId.eq(
					KaleoNotificationTable.INSTANCE.kaleoClassPK
				).and(
					KaleoNotificationTable.INSTANCE.kaleoClassName.eq(
						KaleoNode.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				KaleoTimerTable.INSTANCE
			).innerJoinON(
				KaleoNodeTable.INSTANCE,
				KaleoNodeTable.INSTANCE.kaleoNodeId.eq(
					KaleoTimerTable.INSTANCE.kaleoClassPK
				).and(
					KaleoTimerTable.INSTANCE.kaleoClassName.eq(
						KaleoNode.class.getName())
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoNodeTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoNodeTable.INSTANCE
		).singleColumnReference(
			KaleoNodeTable.INSTANCE.kaleoDefinitionVersionId,
			KaleoDefinitionVersionTable.INSTANCE.kaleoDefinitionVersionId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoNodePersistence;
	}

	@Override
	public KaleoNodeTable getTable() {
		return KaleoNodeTable.INSTANCE;
	}

	@Reference
	private KaleoNodePersistence _kaleoNodePersistence;

}
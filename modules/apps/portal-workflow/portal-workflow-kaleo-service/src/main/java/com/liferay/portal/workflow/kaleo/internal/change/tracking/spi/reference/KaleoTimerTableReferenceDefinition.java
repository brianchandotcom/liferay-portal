/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoActionTable;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTimer;
import com.liferay.portal.workflow.kaleo.model.KaleoTimerTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTimerPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoTimerTableReferenceDefinition
	implements TableReferenceDefinition<KaleoTimerTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoTimerTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				KaleoActionTable.INSTANCE
			).innerJoinON(
				KaleoTimerTable.INSTANCE,
				KaleoTimerTable.INSTANCE.kaleoTimerId.eq(
					KaleoActionTable.INSTANCE.kaleoClassPK
				).and(
					KaleoActionTable.INSTANCE.kaleoClassName.eq(
						KaleoTimer.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				KaleoNotificationTable.INSTANCE
			).innerJoinON(
				KaleoTimerTable.INSTANCE,
				KaleoTimerTable.INSTANCE.kaleoTimerId.eq(
					KaleoNotificationTable.INSTANCE.kaleoClassPK
				).and(
					KaleoNotificationTable.INSTANCE.kaleoClassName.eq(
						KaleoTimer.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				KaleoTaskAssignmentTable.INSTANCE
			).innerJoinON(
				KaleoTimerTable.INSTANCE,
				KaleoTimerTable.INSTANCE.kaleoTimerId.eq(
					KaleoTaskAssignmentTable.INSTANCE.kaleoClassPK
				).and(
					KaleoTaskAssignmentTable.INSTANCE.kaleoClassName.eq(
						KaleoTimer.class.getName())
				)
			)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoTimerTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(KaleoTimerTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoTimerPersistence;
	}

	@Override
	public KaleoTimerTable getTable() {
		return KaleoTimerTable.INSTANCE;
	}

	@Reference
	private KaleoTimerPersistence _kaleoTimerPersistence;

}
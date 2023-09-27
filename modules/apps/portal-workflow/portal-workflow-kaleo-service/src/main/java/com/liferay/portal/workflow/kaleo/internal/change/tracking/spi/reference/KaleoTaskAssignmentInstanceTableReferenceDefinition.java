/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstanceTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskAssignmentInstancePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoTaskAssignmentInstanceTableReferenceDefinition
	implements TableReferenceDefinition<KaleoTaskAssignmentInstanceTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoTaskAssignmentInstanceTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoTaskAssignmentInstanceTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoTaskAssignmentInstanceTable.INSTANCE
		).singleColumnReference(
			KaleoTaskAssignmentInstanceTable.INSTANCE.kaleoTaskInstanceTokenId,
			KaleoTaskInstanceTokenTable.INSTANCE.kaleoTaskInstanceTokenId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoTaskAssignmentInstancePersistence;
	}

	@Override
	public KaleoTaskAssignmentInstanceTable getTable() {
		return KaleoTaskAssignmentInstanceTable.INSTANCE;
	}

	@Reference
	private KaleoTaskAssignmentInstancePersistence
		_kaleoTaskAssignmentInstancePersistence;

}
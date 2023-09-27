/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstanceTable;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskInstanceTokenPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoTaskInstanceTokenTableReferenceDefinition
	implements TableReferenceDefinition<KaleoTaskInstanceTokenTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoTaskInstanceTokenTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			KaleoTaskInstanceTokenTable.INSTANCE.kaleoTaskInstanceTokenId,
			KaleoTaskAssignmentInstanceTable.INSTANCE.kaleoTaskInstanceTokenId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoTaskInstanceTokenTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			KaleoTaskInstanceTokenTable.INSTANCE
		).singleColumnReference(
			KaleoTaskInstanceTokenTable.INSTANCE.kaleoInstanceTokenId,
			KaleoInstanceTokenTable.INSTANCE.kaleoInstanceTokenId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoTaskInstanceTokenPersistence;
	}

	@Override
	public KaleoTaskInstanceTokenTable getTable() {
		return KaleoTaskInstanceTokenTable.INSTANCE;
	}

	@Reference
	private KaleoTaskInstanceTokenPersistence
		_kaleoTaskInstanceTokenPersistence;

}
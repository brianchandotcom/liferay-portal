/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.WorkflowInstanceLinkTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.WorkflowInstanceLinkPersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceTable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class WorkflowInstanceLinkTableReferenceDefinition
	implements TableReferenceDefinition<WorkflowInstanceLinkTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<WorkflowInstanceLinkTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			WorkflowInstanceLinkTable.INSTANCE.workflowInstanceId,
			KaleoInstanceTable.INSTANCE.kaleoInstanceId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<WorkflowInstanceLinkTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			WorkflowInstanceLinkTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _workflowInstanceLinkPersistence;
	}

	@Override
	public WorkflowInstanceLinkTable getTable() {
		return WorkflowInstanceLinkTable.INSTANCE;
	}

	@Reference
	private WorkflowInstanceLinkPersistence _workflowInstanceLinkPersistence;

}
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
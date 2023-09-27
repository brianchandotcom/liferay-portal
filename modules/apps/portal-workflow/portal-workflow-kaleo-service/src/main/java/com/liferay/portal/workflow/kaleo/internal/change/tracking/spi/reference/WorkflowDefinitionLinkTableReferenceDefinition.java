/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.WorkflowDefinitionLinkTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.WorkflowDefinitionLinkPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class WorkflowDefinitionLinkTableReferenceDefinition
	implements TableReferenceDefinition<WorkflowDefinitionLinkTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<WorkflowDefinitionLinkTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<WorkflowDefinitionLinkTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			WorkflowDefinitionLinkTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _workflowDefinitionLinkPersistence;
	}

	@Override
	public WorkflowDefinitionLinkTable getTable() {
		return WorkflowDefinitionLinkTable.INSTANCE;
	}

	@Reference
	private WorkflowDefinitionLinkPersistence
		_workflowDefinitionLinkPersistence;

}
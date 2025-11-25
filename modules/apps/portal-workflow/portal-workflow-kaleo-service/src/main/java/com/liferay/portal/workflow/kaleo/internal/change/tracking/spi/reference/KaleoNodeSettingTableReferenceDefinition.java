/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeSettingTable;
import com.liferay.portal.workflow.kaleo.model.KaleoNodeTable;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoNodeSettingPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = TableReferenceDefinition.class)
public class KaleoNodeSettingTableReferenceDefinition
	implements TableReferenceDefinition<KaleoNodeSettingTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<KaleoNodeSettingTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<KaleoNodeSettingTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			KaleoNodeSettingTable.INSTANCE.kaleoNodeId,
			KaleoNodeTable.INSTANCE.kaleoNodeId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _kaleoNodeSettingPersistence;
	}

	@Override
	public KaleoNodeSettingTable getTable() {
		return KaleoNodeSettingTable.INSTANCE;
	}

	@Reference
	private KaleoNodeSettingPersistence _kaleoNodeSettingPersistence;

}